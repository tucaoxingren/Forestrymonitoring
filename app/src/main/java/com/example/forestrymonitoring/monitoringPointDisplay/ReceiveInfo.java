package com.example.forestrymonitoring.monitoringPointDisplay;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.R;

import java.util.ArrayList;
/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 接收数据并进行封装
 */

public class ReceiveInfo {

    private ArrayList<MonitoringPoint> monArray = new ArrayList<>();
    public static final String ICON_PATH = "./src/main/res/raw/";

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
     * @param mBaiduMap
     */
    public void pullInfo(BaiduMap mBaiduMap,int[] iconId){
        // 从监听到的蓝牙获取信息
        //code

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
        MonitoringPoint monitoringPoint1 = new MonitoringPoint(25.04056,102.73911,26.5,75,ICON_PATH+"a1.png","1号监测点");
        MonitoringPoint monitoringPoint2 = new MonitoringPoint(25.08056,102.77911,25.4,94,ICON_PATH+"a2.png","2号监测点");
        MonitoringPoint monitoringPoint3 = new MonitoringPoint(25.09056,102.79911,27.1,63,ICON_PATH+"a3.png","3号监测点");

        monArray.add(monitoringPoint1);
        monArray.add(monitoringPoint2);
        monArray.add(monitoringPoint3);

    }

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
        return name+"\n  纬度 : "+latitude+"\n  经度 : "+longitude+"\n  温度 : "+temperature+"        湿度 : "+humidity;
    }

    /**
     * 遍历monArray找出对应坐标监测点信息
     * @param latLng
     * @return MonitoringPoint
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
