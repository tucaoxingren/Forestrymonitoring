package com.example.forestrymonitoring.monitoringPointDisplay;

import android.bluetooth.BluetoothDevice;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.bluetoothCommunication.BluetoothClient;
import com.example.forestrymonitoring.bluetoothCommunication.BluetoothManager;
import com.example.forestrymonitoring.bluetoothCommunication.BluetoothServer;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 接收数据并进行封装
 */

public class ReceiveInfo {

    private ArrayList<MonitoringPoint> monArray = new ArrayList<>();
    private static final String ICON_PATH = "./src/main/res/raw/";

    /**
     *  通过蓝牙接收监测点信息
     * @return 信息字符串
     */
    private String  BluetoothReceInfo(){
        String str = null;
        return str;
    }

    /**
     *  主线程最先调用此方法
     * @param mBaiduMap 百度地图视图
     */
    public void pullInfo(BaiduMap mBaiduMap,int[] iconId) {//throws IOException
        String deviceMac = "38:A4:ED:3C:FC:9C";//  MI5
        // 从监听到的蓝牙获取信息
        /*
        // 启动服务端
        BluetoothServer bluetoothServer = new BluetoothServer();
        bluetoothServer.openServe();
        // 启动监听
        BluetoothDevice bluetoothDevice;
        bluetoothDevice = BluetoothManager.getDevice(deviceMac);
        BluetoothClient bluetoothClient = new BluetoothClient(bluetoothDevice);
        bluetoothClient.connetServer();
        */


        //将受到的信息封装
        PackageMonitoringPoint();
        //返回并展示
        PackageMonitoringPoint(mBaiduMap,monArray,iconId);
    }

    /**
     * 封装监测点信息在一个list中
     * @return ArrayList<MonitoringPoint>
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
     * 封装监测点信息在一个list中
     * @return ArrayList<MonitoringPoint> 监测点
     */
    private ArrayList<MonitoringPoint> PackageMonitoringPoint(String info){

        return monArray;
    }

    /**
     *  在地图上添加标记
     * @param  mBaiduMap
     */
    public void PackageMonitoringPoint(BaiduMap mBaiduMap,ArrayList<MonitoringPoint> arrayList,int[] iconId){

        LatLng point;
        BitmapDescriptor bitmap;
        OverlayOptions option;
        int i = 0;
        for (MonitoringPoint monArray : arrayList){
            //定义Maker坐标点
            point = new LatLng(monArray.getLatitude(),monArray.getLongitude());
            //构建Marker图标
            bitmap = BitmapDescriptorFactory.fromResource(iconId[i]);
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions().position(point).icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            //
            i++;
        }
/*
        //定义Maker坐标点
        LatLng point = new LatLng(25.040560,102.739110);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);*/
    }

    /**
     *  根据坐标查找对应坐标的监测点信息
     * @param latLng
     * @return
     */
    public String returnMounInfo(LatLng latLng, TextView textView){
        String info = BluetoothReceInfo();
        PackageMonitoringPoint(info);
        // 根据坐标查找监测点
        MonitoringPoint mo = findMonPoInfo(latLng);
        double latitude = mo.getLatitude();
        double longitude = mo.getLongitude();
        double temperature = mo.getTemperature();
        double humidity = mo.getHumidity();
        String img = mo.getImg();
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
        return name+"\n  纬度 : "+latitude+"\n  经度 : "+longitude+"\n  度 : "+temperature+"        湿度 : "+humidity;
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

}
