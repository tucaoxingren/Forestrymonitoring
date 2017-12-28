package com.example.forestrymonitoring.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 吐槽星人 on 2017/12/27 0027.
 */

public class HttpDownloader {
    private URL url = null;

    /**
     *  根据url下载文件，只支持文本文件，函数的返回值即文件中的内容
     * @param urlStr 文件地址
     * @return 网络文件内容
     */
    public String download(String urlStr){
        BufferedReader buffer = null;
        HttpURLConnection urlConnection;
        StringBuilder sBuilder = new StringBuilder();
        try{
            // 创建url对象
            url = new URL(urlStr);
            // 创建一个HTTP连接
            // 适用于 http连接以及有CA证书的https连接
            // 无CA证书的https连接会抛异常（如12306网站）此时需自定义证书（暂时未完成）
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            String line;
            // 使用IO流读取数据
            buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while((line = buffer.readLine()) != null){
                sBuilder.append(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                buffer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return sBuilder.toString();
    }
    /**
     * 下载文件至指定路径
     * @param urlStr url地址
     * @param path  文件保存路径
     * @param fileName  文件名
     * @return 执行标志 返回-1：代表下载文件出错  返回0：代表下载文件成功    返回1：代表文件已经存在
     */
    public int downloadFile(String urlStr,String path,String fileName){
        InputStream inputStream = null;
        try{
            FileUtils fileUtils = new FileUtils();
            if(fileUtils.isFileExist(path+fileName)){
            	return 1;
            }else{
            	inputStream = getInputStreamFormUrl(urlStr);
                File resultFile = fileUtils.write2SDFromInput(path,fileName,inputStream);
            	if(resultFile == null){
            		return -1;
            	}
            }
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        } finally {
            try{
            	inputStream.close();
            } catch(Exception e){
            	e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     *  根据url得到输入流
     * @param urlStr url地址
     * @return  输入流
     * @throws MalformedURLException .
     * @throws IOException .
     */
    private InputStream getInputStreamFormUrl(String urlStr) throws IOException , MalformedURLException {
    	url = new URL(urlStr);
    	HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
    	InputStream inputStream = urlConn.getInputStream();
    	return inputStream;
    }
}
