package com.example.forestrymonitoring.monitoringPointDisplay;

import android.location.Location;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.forestrymonitoring.R;

/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 接收数据并进行封装
 */

public class ReceiveInfo {

    public Location PackageMonitoringPoint(){
        Location location = null;
        MonitoringPoint monitoringPoint1 = new MonitoringPoint(25.04056,102.95911,25.5,85,"./monitorImg/1.jpg");
        MonitoringPoint monitoringPoint2 = new MonitoringPoint(25.08056,102.55911,25.5,85,"./monitorImg/1.jpg");
        MonitoringPoint monitoringPoint3 = new MonitoringPoint(25.09056,102.35911,25.5,85,"./monitorImg/1.jpeg");

        return location;
    }

    public void PackageMonitoringPoint(BaiduMap mBaiduMap){
        //定义Maker坐标点
        LatLng point = new LatLng(25.040560,102.959110);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

}
