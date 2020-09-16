package com.pan.util;

import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * mvn打包
 * @author tangpan
 */
public class MVNUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(MVNUtils.class);

    /**
     * mvn打包
     * @param pomUrl 打包文件的pom.xml路径
     * @param mvnCommand mvn执行命令
     * @param mavenHomeUrl mavenHome的路径
     */
    public static void mvnPackage(String pomUrl, String mvnCommand, String mavenHomeUrl){
        InvocationRequest request = new DefaultInvocationRequest();
        //pom.xml文件
        //request.setPomFile(new File("E:\\workspace\\QuickDeployment\\file\\QuickDeployment\\QuickDeployment-master\\pom.xml"));
        request.setPomFile(new File(pomUrl));
        //执行的maven命令
        request.setGoals(Collections.singletonList(mvnCommand));

        Invoker invoker = new DefaultInvoker();
        //mavenHome
        invoker.setMavenHome(new File(mavenHomeUrl));
//      自定义输出
//        invoker.setLogger(new PrintStreamLogger(System.err,  InvokerLogger.ERROR){
//        } );
//        invoker.setOutputHandler(new InvocationOutputHandler() {
//            @Override
//            public void consumeLine(String s) throws IOException {
//            }
//        });
        //执行命令
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
//        try{
//            if(invoker.execute(request).getExitCode()==0){
//                  System.out.println("success");
//            }else{
//                  System.err.println("error");
//            }
//        }catch (MavenInvocationException e) {
//            e.printStackTrace();
//        }
    }
}
