package com.pan.util;

import com.pan.entity.ResultEntity;
import com.pan.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件下载
 * @author tangpan
 */
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
    public static ResultEntity downLoadNet(String downLoadFileUrl, String outPutFileUrl){
        //二进制长度
        int byteread = 0;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        //返回对象
        int code = -1;
        String message = "";
        try {
            //创建文件路径流
            URL url = new URL(downLoadFileUrl);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            //写入文件流
            fileOutputStream = new FileOutputStream(outPutFileUrl);

            byte[] buffer = new byte[1024];
            while ((byteread = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer,0, byteread);
            }
            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
        } catch (MalformedURLException e) {
            LOGGER.error("URL协议、格式或者路径错误");
            code = ResultEnum.EXCEPTION.getCode();
            message = e.getMessage();
            e.printStackTrace();
        } catch (IOException e){
            code = ResultEnum.EXCEPTION.getCode();
            message = e.getMessage();
            LOGGER.error("文件流读取文件错误");
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("文件流关闭失败");
                e.printStackTrace();
            }
        }
        return new ResultEntity(code, message);
    }
}
