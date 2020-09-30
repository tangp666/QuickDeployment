package com.pan.service.impl;

import com.pan.dao.TProjectServerDao;
import com.pan.dao.TServerInfoDao;
import com.pan.entity.TProjectServerEntity;
import com.pan.entity.TServerInfoEntity;
import com.pan.query.Tree;
import com.pan.service.TServerInfoService;
import com.pan.util.BuildTreeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实现
 * @author tangpan
 */
@Service("tServerInfoService")
public class TServiceInfoImpl implements TServerInfoService {

    @Resource
    private TServerInfoDao tServerInfoDao;

    @Resource
    private TProjectServerDao tProjectServerDao;

    @Override
    public TServerInfoEntity findById(long id) {
        return tServerInfoDao.findById(id);
    }

    @Override
    public List<TServerInfoEntity> findALL() {
        return tServerInfoDao.findALL();
    }

    @Override
    public int countAll() {
        return tServerInfoDao.countAll();
    }

    @Override
    public List<TServerInfoEntity> findByParames(Map<String, Object> map) {
        return tServerInfoDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tServerInfoDao.countByParames(map);
    }

    @Override
    public int insert(TServerInfoEntity tServerInfoEntity) {
        return tServerInfoDao.insert(tServerInfoEntity);
    }

    @Override
    public int update(TServerInfoEntity tServerInfoEntity) {
        return tServerInfoDao.update(tServerInfoEntity);
    }

    @Override
    public int deleteById(long id) {
        return tServerInfoDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tServerInfoDao.deleteBatch(ids);
    }

    @Override
    public Tree<TServerInfoEntity> treeServerData(long projectId) {
        //通过项目id查询关联的信息
        Map<String,Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<TProjectServerEntity> tProjectServerEntities = tProjectServerDao.findByParames(map);
        //获取服务器列表集合
        List<Long> serverList = tProjectServerEntities.stream().map(tProjectServerEntity -> {
            return tProjectServerEntity.getServerId();
        }).collect(Collectors.toList());

        //查询所有的服务器
        List<TServerInfoEntity> tServerInfoEntities = tServerInfoDao.findALL();
        List<Tree<TServerInfoEntity>> trees = new ArrayList<Tree<TServerInfoEntity>>();
        if(tServerInfoEntities != null){
            //遍历服务器
            for(TServerInfoEntity tServerInfoEntity : tServerInfoEntities){
                Tree<TServerInfoEntity> tree = new Tree<>();
                tree.setId("s" + tServerInfoEntity.getId());
                tree.setText(tServerInfoEntity.getServerName());
                tree.setType("noIcon");
                tree.setParentId("0");
                Map<String, Object> state = new HashMap<>(16);
                state.put("opened", true);
                state.put("level", 2);
                if(serverList.contains(tServerInfoEntity.getId())){
                    state.put("selected", true);
                }else{
                    state.put("selected", false);
                }
                tree.setState(state);
                trees.add(tree);
            }
        }
        Tree<TServerInfoEntity> t = BuildTreeUtils.build(trees);
        Map<String, Object> state = new HashMap<>(16);
        //状态信息添加进tree
        state.put("opened", true);
        t.setText("所有服务器");
        t.setState(state);
        return t;
    }
}
