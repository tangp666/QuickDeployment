package com.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(DownLoadUtils.class);

    /**
     * 下载网络文件
     * @param downLoadFileUrl 下载的文件路径
     * @param outPutFileUrl 文件写入的路径
     *
     */
    public static void downLoadNet(String downLoadFileUrl, String outPutFileUrl){
        //二进制长度
        int byteread = 0;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            //创建文件路径流
            URL url = new URL(downLoadFileUrl);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            //写入文件流
            fos = new FileOutputStream(outPutFileUrl);

            byte[] buffer = new byte[1024];
            while ((byteread = inputStream.read(buffer)) > 0){
                System.out.println(byteread);
                fos.write(buffer,0, byteread);
            }
        } catch (MalformedURLException e) {
            LOGGER.error("URL协议、格式或者路径错误");
            e.printStackTrace();
        } catch (IOException e){
            LOGGER.error("文件流读取文件错误");
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                LOGGER.error("文件流关闭失败");
                e.printStackTrace();
            }
        }
    }
}
