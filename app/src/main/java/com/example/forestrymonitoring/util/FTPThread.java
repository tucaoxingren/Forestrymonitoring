package com.example.forestrymonitoring.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MarkerOptions;
import com.example.forestrymonitoring.common.ChatConstant;
import com.example.forestrymonitoring.monitoringPointDisplay.MonitoringPoint;
import com.example.forestrymonitoring.monitoringPointDisplay.ReceiveInfo;

import java.util.ArrayList;
import java.util.List;

public class FTPThread implements Runnable{

	//FTP工具类
    private FTPUtils ftpUtils = null;
    //刷新地图参数
	private BaiduMap mBaiduMap = null;
	private List<MarkerOptions> markerOptionsList;
	private int[] iconId = new int[7];//图标Id
	private Context mContext;
	private float alpha;

	private ArrayList<MonitoringPoint> monArray = new ArrayList<>();
	private ReceiveInfo receiveInfo = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare();
		boolean flag = InitFTPServerSetting(); 
		if(flag)
			System.out.println("connect success");
		String FileName = "pointInfo.txt";
		String FilePath = ChatConstant.appDirectory+"/"+FileName;
		System.out.println("FilePath:"+FilePath);
		if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            Log.i("info", "1,需要申请权限。");
			Toast.makeText(mContext,"请检查文件读写权限",Toast.LENGTH_SHORT).show();
		}
		else{
			System.out.println("start download");
			if(NetWorkUtils.isNetworkConnected(mContext)) {
				ftpUtils.downLoadFile(FilePath,FileName,ChatConstant.ftpDatePath);
				// 刷新并展示坐标
				RefreshInterface();

				// 下载监测点图片文件
				// 获取图片名
				monArray = receiveInfo.getMonArray();
				String [] images = new String[monArray.size()];
				for (int i=0;i<monArray.size();i++) {
					images[i] = monArray.get(i).getImg();
				}
				for (int i = 0; i < images.length; i++) {
					String imagePath = ChatConstant.appDirectory+"/"+images[i];
					ftpUtils.downLoadFile(imagePath,images[i],ChatConstant.ftpImagePath);
				}
			}
			else{
				Log.i("info", "无网络连接");
				Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
			}
		}
		Looper.loop();
	}

	/**
	 * 初始化ftp服务器连接
	 * @return 连接标记 true：成功	 <br> false：失败
	 */
	private boolean InitFTPServerSetting() {
        // TODO Auto-generated method stub  
        ftpUtils = FTPUtils.getInstance();
        //cs3.swfu.edu.cn  20141151062  19951024
        boolean flag = ftpUtils.initFTPSetting("202.203.132.245", 21, "20141151062", "19951024");
        return flag;
    }

	/**
	 * 刷新并展示坐标
	 */
	private void RefreshInterface(){
		clearOverlay();
		receiveInfo = new ReceiveInfo();
		markerOptionsList = receiveInfo.pullInfo(mBaiduMap,iconId,alpha);
		if(!receiveInfo.isOperatingFlag())
		{
			Toast.makeText(mContext,"重试",Toast.LENGTH_SHORT).show();
			System.out.print("获取坐标失败");
		}
	}

	// 传入参数
	public void setParam(BaiduMap mBaiduMap,List<MarkerOptions> markerOptionsList,int[] iconId,Context mContext,float alpha){
		this.mBaiduMap = mBaiduMap;
		this.markerOptionsList = markerOptionsList;
		this.iconId = iconId;
		this.mContext = mContext;
		this.alpha = alpha;
	}

	// 清除展示点
	private void clearOverlay() {
		mBaiduMap.clear();
	}
}