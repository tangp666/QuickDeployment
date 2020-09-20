package com.pan;

import com.pan.entity.SysMenuEntity;
import com.pan.service.SysMenuService;
import com.pan.util.DownLoadUtils;
import com.pan.util.MVNUtils;
import com.pan.util.ZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuickdeploymentApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    void contextLoads() {
        List<SysMenuEntity> all = sysMenuService.findALL();
        System.out.println(all.size());
    }

    /**
     * 下载zip文件
     */
    @Test
    void testDownLoadNet(){
        DownLoadUtils.downLoadNet("https://github.com/tangp666/QuickDeployment/archive/master.zip",
                "E:\\workspace\\QuickDeployment\\file\\QuickDeployment.zip");
    }

    /**
     * 解压文件
     */
    @Test
    void testUnZip(){
        ZipUtils.unZip("E:\\workspace\\QuickDeployment\\file\\QuickDeployment.zip");
    }

    /**
     * mvn打包
     */
    @Test
    void testMvnPackage(){
        MVNUtils.mvnPackage("E:\\workspace\\QuickDeployment\\file\\QuickDeployment\\QuickDeployment-master\\pom.xml",
                //"mvn package",
                "clean package",
                "E:\\apache-maven-3.6.2");
    }
}
