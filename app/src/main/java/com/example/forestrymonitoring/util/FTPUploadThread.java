package com.example.forestrymonitoring.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.example.forestrymonitoring.common.ChatConstant;

/**
 * Created by tucaoxingren on 2018/3/19 0019.
 *
 */

public class FTPUploadThread implements Runnable{
    //FTP工具类
    private FTPUtils ftpUtils = null;
    private Context mContext;
    @Override
    public void run() {
        Looper.prepare();

        boolean flag = InitFTPServerSetting();
        if(flag)
            ftpUtils.uploadFile(ChatConstant.commandFilePath,ChatConstant.controlFileName);
        else
            Toast.makeText(mContext,"连接FTP服务器失败",Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    /**
     * 初始化ftp服务器连接
     * @return 连接标记 true：成功	 <br> false：失败
     */
    private boolean InitFTPServerSetting() {
        ftpUtils = FTPUtils.getInstance();
        boolean flag = ftpUtils.initFTPSetting(ChatConstant.FTPUrl, ChatConstant.FTPPort, ChatConstant.FTPLogin, ChatConstant.FTPPassword);
        return flag;
    }

    /**
     * 传入参数
     * @param mContext context
     */
    public void setParam(Context mContext){
        this.mContext = mContext;
    }
}
