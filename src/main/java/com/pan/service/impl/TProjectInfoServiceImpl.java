package com.pan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pan.dao.TProjectInfoDao;
import com.pan.dao.TProjectServerDao;
import com.pan.entity.ResultEntity;
import com.pan.entity.TProjectInfoEntity;
import com.pan.entity.TServerInfoEntity;
import com.pan.service.TProjectInfoService;
import com.pan.thread.UploadServerTask;
import com.pan.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目信息实现
 *
 * @author tangpan
 */
@Service("tProjectInfoService")
public class TProjectInfoServiceImpl implements TProjectInfoService {

    @Resource
    private TProjectInfoDao tProjectInfoDao;

    @Resource
    private TProjectServerDao tProjectServerDao;

    @Override
    public TProjectInfoEntity findById(long id) {
        return tProjectInfoDao.findById(id);
    }

    @Override
    public List<TProjectInfoEntity> findALL() {
        return tProjectInfoDao.findALL();
    }

    @Override
    public int countAll() {
        return tProjectInfoDao.countAll();
    }

    @Override
    public List<TProjectInfoEntity> findByParames(Map<String, Object> map) {
        return tProjectInfoDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tProjectInfoDao.countByParames(map);
    }

    @Override
    public int insert(TProjectInfoEntity tProjectInfoEntity) {
        return tProjectInfoDao.insert(tProjectInfoEntity);
    }

    @Override
    public int update(TProjectInfoEntity tProjectInfoEntity) {
        return tProjectInfoDao.update(tProjectInfoEntity);
    }

    @Override
    public int deleteById(long id) {
        return tProjectInfoDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tProjectInfoDao.deleteBatch(ids);
    }

    @Override
    public void rebuild(long id) {
        /**
         * 第一步查询项目源码位置 并下载源码
         */
        TProjectInfoEntity tProjectInfoEntity = tProjectInfoDao.findById(id);
        String projectSourceCodeUrl = tProjectInfoEntity.getProjectSourceCodeUrl();
        if (!StringUtils.isNotEmpty(projectSourceCodeUrl)) {
            throw new NullPointerException("项目源码路径不存在");
        }
        //user.dir指定了当前项目的路径
        String propertyUrl = System.getProperty("user.dir");
        //项目路径下的file文件夹
        propertyUrl += "/file";
        File propertyFile = new File(propertyUrl);
        if (!propertyFile.exists()) {
            //若不存在则创建改目录
            propertyFile.mkdirs();
        }
        //判断当前路径下面是否有文件
        //需要写入的文件路径
        String[] split = projectSourceCodeUrl.split("/");
        String toPropertyUrl = propertyUrl + "/" + split[split.length - 1];
        File toPropertyFile = new File(toPropertyUrl);
        if (!toPropertyFile.exists()) {
            try {
                //若不存在则创建改文件
                toPropertyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将网络文件下载至指定文件
        DownLoadUtils.downLoadNet(projectSourceCodeUrl, toPropertyUrl);
        /**
         * 第二步 解压文件， mvn打包文件
         */
        //将下载下来的文件解压至当前文件夹
        ResultEntity resultEntity = ZipUtils.unZip(toPropertyUrl, propertyUrl);
        JSONObject data = resultEntity.getData();
        String filename = "";
        if(StringUtils.isNotEmpty(data)){
            filename = (String) data.get("filename");
        }
        //寻找解压后的pom文件
        String pomUrl = propertyUrl + "/" + filename + "/pom.xml";
        //mvn打包
        MVNUtils.mvnPackage(pomUrl, PropertyUtil.getValue("mvn_order"), PropertyUtil.getValue("mvn_home"));
        /**
         * 第三步查询项目的服务器位置，及服务器源码上传位置
         */
        //寻找mvn打包后的jar文件
        String[] fileList=new File(propertyUrl + "/" + filename + "/target/").list();
        String jarUrl = "";
        for (String fileNmae : fileList) {
            if (StringUtils.isNotEmpty(fileNmae) && fileNmae.endsWith(".jar")){
                jarUrl = propertyUrl + "/" + filename + "target/" + fileNmae;
            }
        }
        if (!StringUtils.isNotEmpty(jarUrl)){
            throw new NullPointerException("mvn打包文件不存在！");
        }
//        Map<String, Object> projectServerMap = new HashMap<>();
//        projectServerMap.put("projectId", id);
//        List<TServerInfoEntity> tServerInfoLists = tProjectServerDao.findTServerInfoLists(projectServerMap);
//        //多线程执行项目上传
//        for (TServerInfoEntity tServerInfo : tServerInfoLists) {
//            /**
//             * 第四步 在多线程执行中进行执行 重启项目
//             */
//            UploadServerTask task = new UploadServerTask(new File(""), tProjectInfoEntity.getSourceCodeName(), tServerInfo);
//            ThreadPoolServiceUtils.getInstance().execute(task);
//        }
    }
}
