package com.example.forestrymonitoring.monitoringPointDisplay;

import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.util.FTPThread;
import com.example.forestrymonitoring.util.FTPUtils;
import com.example.forestrymonitoring.util.FileUtils;
import com.example.forestrymonitoring.util.HttpDownloader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 接收数据并进行封装
 */

public class ReceiveInfo {

    private ArrayList<MonitoringPoint> monArray = new ArrayList<>();
    private static final String ICON_PATH = "./src/main/res/raw/";
    private boolean operatingFlag = true;

    /**
     *  通过蓝牙接收监测点信息
     * @return 信息字符串
     */
    private String  BluetoothReceInfo(){
        //String str = "";
        return "";
    }

    /**
     *  主线程最先调用此方法
     * @param mBaiduMap 百度地图视图
     * @return List<Marker>标记点
     */
    public List<MarkerOptions> pullInfo(BaiduMap mBaiduMap,int[] iconId,float alpha) {
        List<MarkerOptions> markerOptionsList;
        //将收到的信息封装
        boolean flag = transformString();
        if(!flag){
            this.operatingFlag = false;
        }
        //返回并展示
        markerOptionsList = PackageMonitoringPoint(mBaiduMap,monArray,iconId,alpha);
        return markerOptionsList;
    }

    /**
     *  下载文件
     */
    private String downloadFile(){
        HttpDownloader httpDownloader = new HttpDownloader();
        //192.168.1.107:8080/Forestry/info.txt///127.0.0.1:8080
        String url = "https://raw.githubusercontent.com/tucaoxingren/Forestrymonitoring/master/pointInfo.txt";
        //String url = "http://192.168.1.107:8080/HelloWeb/info.txt";
        //String url = "http://www.zealer.com/";
        String result = httpDownloader.download(url);
        System.out.println(result);
        return result;
    }
    /**
     * 封装监测点信息在一个list中
     */
    private void PackageMonitoringPoint(){
        MonitoringPoint monitoringPoint1 = new MonitoringPoint(25.04056f,102.73911f,26.5f,75f,ICON_PATH+"a1.png","1号监测点");
        MonitoringPoint monitoringPoint2 = new MonitoringPoint(25.08056f,102.77911f,25.4f,94f,ICON_PATH+"a2.png","2号监测点");
        MonitoringPoint monitoringPoint3 = new MonitoringPoint(25.09056f,102.79911f,27.1f,63f,ICON_PATH+"a3.png","3号监测点");

        monArray.add(monitoringPoint1);
        monArray.add(monitoringPoint2);
        monArray.add(monitoringPoint3);

    }

    /**
     *
     * @return 获取信息标志，若获取失败，或格式错误返回false
     */
    private boolean transformString(){
        //String str = "25.04056f,102.73911f,26.5f,75f,a1.png,1号监测点,25.08056f,102.77911f,25.5f,94f,a2.png,2号监测点";
        //String str  = downloadFile();

		final String FileName = "pointInfo.txt";
		FileUtils fileUtils = new FileUtils();
		final String FilePath = fileUtils.getSDPATH()+"2forestrymonitoring/"+FileName;
/*
        FTPThread ftpThread = new FTPThread();
        new Thread(ftpThread).start();*/
        /*
		new Thread(new Runnable(){
            FTPUtils ftpUtils = null;
			@Override
			public void run() {
                boolean flag = InitFTPServerSetting();
                if(flag)
                    System.out.println("connect success");
                //ftpUtils = FTPUtils.getInstance();
				ftpUtils.downLoadFile(FilePath,FileName);
			}
            public boolean InitFTPServerSetting() {
                // TODO Auto-generated method stub
                ftpUtils = FTPUtils.getInstance();
                //cs3.swfu.edu.cn  20141151062  19951024
                boolean flag = ftpUtils.initFTPSetting("202.203.132.245", 21, "20141151062", "19951024");
                return flag;
            }
		}).start();*/

		String str = readFileToString(FilePath);
		str = str.substring(0, str.length()-1);// 去除末尾换行符
		
        analysisMonitoringPoint(str);
        if (str=="" || str==null)
            return false;
        return true;
    }
	
	/**
	* 读文件 转化为字符串
	*/
	private String readFileToString(String FilePath){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FilePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    //new一个StringBuffer用于字符串拼接
	    StringBuffer sb = new StringBuffer();
	    String line = null;
	    try {
	        //当输入流内容读取完毕时
	        while ((line = reader.readLine()) != null) {
	             sb.append(line + "\n");
	        }
	        //关闭流数据 节约内存消耗
	        fis.close();
	        reader.close();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return sb.toString();
	}

    /**
     *  解析并封装监测点信息在一个list中
     * @param info 监测点信息
     */
    private String analysisMonitoringPoint(String info){
        String[] temp = info.split(",");
		if(temp.length%6 != 0)
			return "数据校验错误：数据总数错误";

        for(int i=0;i<temp.length/6;i++){
            MonitoringPoint monPoint = new MonitoringPoint();
			try{
				monPoint.setLatitude(Float.parseFloat(temp[i*6+0]));
				monPoint.setLongitude(Float.parseFloat(temp[i*6+1]));
				monPoint.setTemperature(Float.parseFloat(temp[i*6+2]));
				monPoint.setHumidity(Float.parseFloat(temp[i*6+3]));
			} catch (Exception e) {
			}
            monPoint.setImg(temp[i*6+4]);
            monPoint.setName(temp[i*6+5]);
            monArray.add(monPoint);
        }
        return null;
    }

    /**
     * 在地图上添加标记
     * @param mBaiduMap 地图视图
     * @param arrayList 标记点封装类 组
     * @param iconId 图标资源id数组
     * @return List<Marker>
     */
    private List<MarkerOptions> PackageMonitoringPoint(BaiduMap mBaiduMap,ArrayList<MonitoringPoint> arrayList,int[] iconId,float alpha){

        LatLng point;
        BitmapDescriptor bitmap;
        MarkerOptions option;
        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        int i = 0;
        for (MonitoringPoint monArray : arrayList){
            //定义Maker坐标点
            point = new LatLng(monArray.getLatitude(),monArray.getLongitude());
            //构建Marker图标
            bitmap = BitmapDescriptorFactory.fromResource(iconId[i]);
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions().position(point).icon(bitmap);
            // 掉下动画
            option.animateType(MarkerOptions.MarkerAnimateType.drop);
            // Marker 透明度
            option.alpha(alpha);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            markerOptionsList.add(option);
            // 图标数组下标自增
            i++;
        }
        return markerOptionsList;
    }

    /**
     * 改变已经显示的Marker的透明度
     * @param mBaiduMap 地图视图
     * @param markerOptionsList 标记点封装类 组
     */
    public static void PackageMonitoringPoint(BaiduMap mBaiduMap,List<MarkerOptions> markerOptionsList){
        mBaiduMap.clear();
        for (MarkerOptions markerOptions : markerOptionsList){
            markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }
    }

    /**
     *  根据坐标查找对应坐标的监测点信息
     * @param latLng 要查找的经纬度
     * @return String 监测点信息
     */
    public String returnMounInfo(LatLng latLng ){
        String info = BluetoothReceInfo();
        analysisMonitoringPoint(info);
        // 根据坐标查找监测点
        MonitoringPoint mo;
        mo = findMonPoInfo(latLng);
        if (mo == null){
            mo = new MonitoringPoint(0f,0f,0f,0f,"","无此坐标");
        }
        float latitude = mo.getLatitude();      // 纬度
        float longitude = mo.getLongitude();// 经度
        float temperature = mo.getTemperature();
        float humidity = mo.getHumidity();
        //String img = mo.getImg();
        String name = mo.getName();
        /*
        Drawable drawable = new DrawableContainer();
        drawable.createFromPath(img);*/
        //Drawable drawable= getResources().getDrawable(img);

        /// 这一步必须要做,否则不会显示.
        //drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //左上右下
        //textView.setCompoundDrawables(null,null, drawable,null);
        //                           纬度                        经度
        return name+"\n  纬度 : "+latitude+"\n  经度 : "+longitude+"\n  温度 : "+temperature+"        湿度 : "+humidity;
    }

    /**
     * 遍历monArray找出对应坐标监测点信息
     * @param latLng 坐标值
     * @return MonitoringPoint  对应坐标的信息
     */
    private MonitoringPoint findMonPoInfo(LatLng latLng){
        // 遍历monArray找出对应坐标监测点信息
        for (MonitoringPoint mo: monArray) {
            if(mo.getLatitude() == latLng.latitude && mo.getLongitude() == latLng.longitude)
                return mo;
        }
        return null;
    }

    public boolean isOperatingFlag() {
        return operatingFlag;
    }

    public void setOperatingFlag(boolean operatingFlag) {
        this.operatingFlag = operatingFlag;
    }

}
