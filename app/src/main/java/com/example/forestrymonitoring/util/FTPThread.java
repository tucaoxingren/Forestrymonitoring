package com.example.forestrymonitoring.util;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.example.forestrymonitoring.common.ChatConstant;
import com.example.forestrymonitoring.monitoringPointDisplay.ReceiveInfo;

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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = InitFTPServerSetting(); 
		if(flag)
			System.out.println("connect success");
		String FileName = "pointInfo.txt";
		String FilePath = ChatConstant.appDirectory+"/"+FileName;
		System.out.println("FilePath:"+FilePath);
		System.out.println("start download");
		ftpUtils.downLoadFile(FilePath,FileName,mContext);
		// 刷新并展示坐标
		RefreshInterface();
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
		ReceiveInfo receiveInfo = new ReceiveInfo();
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