package com.pan;

import com.pan.util.DownLoadUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuickdeploymentApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("1111111111111111111");
    }

    @Test
    void testDownLoadNet(){
        DownLoadUtils.downLoadNet("https://github.com/tangp666/QuickDeployment/archive/master.zip",
                "E:\\workspace\\QuickDeployment\\src\\main\\resources\\file\\test.zip");
    }

}
