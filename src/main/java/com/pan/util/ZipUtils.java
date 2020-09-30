package com.pan.util;

import com.pan.entity.ResultEntity;
import com.pan.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 解压、压缩zip文件工具类
 * @author tangpan
 */
public class ZipUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 数组长度
     */
    private static final int buffer = 2048;
    /**
     * 解压zip文件
     * @param fileUrl 解压文件 文件路径
     */
    public static ResultEntity unZip(String fileUrl){
        //解压后文件路径
        String savePath = fileUrl.substring(0,fileUrl.lastIndexOf(".")) + File.separator;
        //判断当前文件路径是否存在文件夹 不存在即创建一个
        File unFile = new File(savePath);
        if(!unFile.exists()){
            unFile.mkdir();
        }
        return zip(fileUrl, savePath);
    }

    /**
     * 解压文件
     * @param fileUrl 文件路径
     * @param descDir 文件解压位置
     */
    public static ResultEntity unZip(String fileUrl, String descDir){
        //判断 解压文件夹是否存在
        File pathFile = new File(descDir);
        if(!pathFile.exists())
        {
            pathFile.mkdirs();
        }
        return zip(fileUrl, descDir);
    }

    /**
     * 处理压缩文件
     * @param fileUrl 解压文件路径
     * @param savePath 解压位置
     */
    private static ResultEntity zip(String fileUrl, String savePath){
        int count = -1;
        //文件流
        File file = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ZipFile zipFile = null;
        //返回对象
        int code = -1;
        String message = "";
        try {
            zipFile = new ZipFile(fileUrl);
            //File自身的遍历对象
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                byte[] buf = new byte[buffer];
                ZipEntry entry = (ZipEntry)entries.nextElement();
                //文件名称
                String filename = entry.getName();
                //目录是否存在
                boolean ismkdir = false;
                //检验此文件是否带有文件夹
                if(filename.lastIndexOf("/") != 0){
                    ismkdir = true;
                }
                filename = savePath + "/" + filename;
                //如果是文件夹 创建
                if(entry.isDirectory()){
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                //如果是目录先创建
                if(!file.exists()){
                    if(ismkdir){
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //目录先创建
                    }
                }
                file.createNewFile(); //创建文件

                //读取文件
                inputStream = zipFile.getInputStream(entry);
                //写文件
                fileOutputStream = new FileOutputStream(file);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream, buffer);
                while ((count = inputStream.read(buf)) > 0){
                    bufferedOutputStream.write(buf,0, count);
                }
                bufferedOutputStream.flush();
            }
            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
        } catch (IOException e) {
            LOGGER.error("文件解压错误");
            code = ResultEnum.SUCCESS.getCode();
            message = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                //关闭全部流
                if(bufferedOutputStream != null){
                    bufferedOutputStream.close();
                }
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
                if(zipFile != null){
                    zipFile.close();
                }
            } catch (IOException e) {
                LOGGER.error("关闭文件流失败");
                e.printStackTrace();
            }
        }
        return new ResultEntity(code,message);
    }


}
