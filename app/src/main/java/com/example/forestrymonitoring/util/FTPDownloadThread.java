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

public class FTPDownloadThread implements Runnable{

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
		Looper.prepare();
		boolean flag = InitFTPServerSetting();
		if(flag)
			System.out.println("connect success");
		String FilePath = ChatConstant.appDirectory+ChatConstant.dateName;
		String imgFilePath = ChatConstant.appDirectory+ChatConstant.imgListName;
		System.out.println("FilePath:"+FilePath);
		if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            Log.i("info", "1,需要申请权限。");
			Toast.makeText(mContext,"请检查文件读写权限",Toast.LENGTH_SHORT).show();
		}
		else {
			System.out.println("start download");
			if (NetWorkUtils.isNetworkConnected(mContext)) {
				// 下载数据文件
				if (ftpUtils.downLoadFile(FilePath, ChatConstant.dateName, ChatConstant.ftpDatePath)) {
					Log.d("FTPDownload", ChatConstant.dateName + "  download success");
					// 下载图片列表文件
					if (ftpUtils.downLoadFile(imgFilePath, ChatConstant.imgListName, ChatConstant.ftpDatePath)) {
						Log.d("FTPDownload", ChatConstant.imgListName + "  download success");
						// 修改pointInfo文件
						changePointInfo();
						// 刷新并展示坐标
						RefreshInterface();
						// 下载监测点图片文件
						// 获取图片名
						monArray = receiveInfo.getMonArray();
						String[] images = new String[monArray.size()];
						for (int i = 0; i < monArray.size(); i++) {
							images[i] = monArray.get(i).getImg();
						}
						for (int i = 0; i < images.length; i++) {
							String imagePath = ChatConstant.appDirectory + images[i];
							ftpUtils.downLoadFile(imagePath, images[i], ChatConstant.ftpImagePath);
						}
						//下载wav文件
						// 读 pictureList.txt
						String imgList = FileUtils.readFileToString(ChatConstant.imgListPath);
						// 将字符串切割成字符串数组
						String [] imgListArray = imgList.split(ChatConstant.huanhangfu);
						// pictureList.txt文件倒数第二行为最新的wav文件名
						String wavName = imgListArray[imgListArray.length-1];
						// 下载wav文件
						ftpUtils.downLoadFile(ChatConstant.appDirectory +"music.wav", wavName, ChatConstant.ftpImagePath);
					} else {
						Log.d("FTPDownload", ChatConstant.imgListName + "  download fail");
						Toast.makeText(mContext, "下载数据文件失败，请重试", Toast.LENGTH_SHORT).show();
					}
				}else {
					Log.d("FTPDownload", ChatConstant.dateName + "  download fail");
					Toast.makeText(mContext, "下载数据文件失败，请重试", Toast.LENGTH_SHORT).show();
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
	 * 改变pointInfo中的图片名
	 */
	private void changePointInfo(){
		// 读 图片列表文件
		String imgList = FileUtils.readFileToString(ChatConstant.imgListPath);
		// 读 数据文件
		String dateFile = FileUtils.readFileToString(ChatConstant.datePath);
		// 将字符串切割成字符串数组
		String [] imgListArray = imgList.split(ChatConstant.huanhangfu);
		String [] dateFileArray = dateFile.split(ChatConstant.douhao);
		int dateLength = 0;
		if(dateFileArray.length%6!=0)
			dateLength = dateFileArray.length-1;
		else
			dateLength = dateFileArray.length;
//		for (int i = 0;i < dateLength/6;i++){
//			if(dateLength/6 == (imgListArray.length-1)/2)
//				dateFileArray[6*i+4] = imgListArray[2*i+1];
//			else
//				dateFileArray[6*i+4] = imgListArray[1];
//		}
		// 为pointInfo.txt赋值 图片名
		for (int i = 0; i < dateLength/6; i++) {
			// pictureList.txt文件倒数第二行为最新的img文件名
			dateFileArray[6*i+4] = imgListArray[imgListArray.length-2];
		}
		StringBuffer stringBuffer = new StringBuffer();
		// 拼接string数组
		for (int i = 0; i < dateFileArray.length; i++) {
			stringBuffer.append(dateFileArray[i]+ChatConstant.huanhangfu);
		}
		// 移除3个换行符\n
		stringBuffer.delete(stringBuffer.length()-3,stringBuffer.length());
		String changFinishDate = stringBuffer.toString();
		// 删除pointInfo.txt
		FileUtils.deleteFile(ChatConstant.datePath);
		// 重新写入pointInfo.txt
		FileUtils.wirteFile(ChatConstant.datePath,changFinishDate);

	}
	/**
	 * 初始化ftp服务器连接
	 * @return 连接标记 true：成功	 <br> false：失败
	 */
	private boolean InitFTPServerSetting() {
        // TODO Auto-generated method stub  
        ftpUtils = FTPUtils.getInstance();
        boolean flag = ftpUtils.initFTPSetting(ChatConstant.FTPUrl, ChatConstant.FTPPort, ChatConstant.FTPLogin, ChatConstant.FTPPassword);
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