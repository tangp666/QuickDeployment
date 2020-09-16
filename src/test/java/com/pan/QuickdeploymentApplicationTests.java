package com.pan;

import com.pan.util.DownLoadUtils;
import com.pan.util.ZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuickdeploymentApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("1111111111111111111");
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

}
