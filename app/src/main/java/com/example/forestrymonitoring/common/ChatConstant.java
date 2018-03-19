package com.example.forestrymonitoring.common;

import android.os.Environment;

import java.util.UUID;

/**
 * @Description:  常量
 * @author: tucaoxingren
 * @date: 16/9/18 20:36.
 */
public class ChatConstant {

    // FTP服务器端常量
    /**
     * FTP数据文件名
     */
    public static final String dateName = "pointInfo.txt";
    /**
     * FTP存储图片名的文件文件名
     */
    public static final String imgListName = "pictureList.txt";
    /**
     * FTP数据文件在ftp服务器上的地址
     */
    public static final String ftpDatePath = "./";
    /**
     * FTP图片文件在ftp服务器上的地址
     */
    public static final String ftpImagePath = "./";
    /**
     * FTP控制指令文件名
     */
    public static final String controlFileName = "serial.dat";
    /**
     * FTP服务器地址
     */
    public static final String FTPUrl = "39.106.124.208";
    /**
     * FTP服务器端口号
     */
    public static final int FTPPort = 21;
    /**
     * FTP服务器登录名
     */
    public static final String FTPLogin= "naxyftp";
    /**
     * FTP服务器登录密码
     */
    public static final String FTPPassword = "0987";


    // APP本地端常量
    /**
     * 本地应用目录
     */
    public static final String appDirectory = Environment.getExternalStorageDirectory()+"/"+"forestrymonitoring/";
    /**
     * 本地数据文件路径+文件名
     */
    public static final String datePath = appDirectory+dateName;
    /**
     * 本地图片列表文件路径+文件名
     */
    public static final String imgListPath = appDirectory+imgListName;
    /**
     * 本地控制指令文件路径+文件名
     */
    public static final String commandFilePath = appDirectory+controlFileName;


    /**
     * 上
     */
    public static final int up = 1;
    /**
     * 下
     */
    public static final int down = 2;
    /**
     * 左
     */
    public static final int left = 4;
    /**
     * 右
     */
    public static final int right = 3;
    /**
     * 切割符    “,“
     */
    public static final String douhao= ",";
    /**
     * 切割符    “\n“
     */
    public static final String huanhangfu= "\n";

}
