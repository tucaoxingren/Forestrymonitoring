package com.example.forestrymonitoring.common;

import android.os.Environment;

import java.util.UUID;

/**
 * @Description: 常量
 * @author: tucaoxingren
 * @date: 16/9/18 20:36.
 */
public class ChatConstant {

    /*应用目录*/
    public static final String appDirectory = Environment.getExternalStorageDirectory()+"/"+"forestrymonitoring";
    /*数据文件名*/
    public static final String dateName = "pointInfo.txt";
    /*数据文件路径+文件名*/
    public static final String datePath = appDirectory+"/"+dateName;
    /*数据文件在ftp服务器上的地址*/
    public static final String ftpDatePath = "/public";
    /*图片文件在ftp服务器上的地址*/
    public static final String ftpImagePath = "/public/images";

}
