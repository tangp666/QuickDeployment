package com.pan;

import com.pan.util.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 * @author tangpan
 */
@MapperScan("com.pan.dao")
@SpringBootApplication
public class QuickdeploymentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(QuickdeploymentApplication.class, args);
        //将自定义的不通bean对象交由spring管理
        BeanUtils.applicationContext = run;
    }

}
