package com.example.forestrymonitoring.monitoringPointDisplay;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 封装监测点信息
 * 包括经纬度 温度 湿度 图片路径
 */

public class MonitoringPoint {

    private float longitude;//经度
    private float latitude;//纬度
    private float temperature;//温度
    private float humidity;//湿度
    private String img = null;//监测点图片
    private String name = null;//监测点名字或标号

    public MonitoringPoint(){

    }
    public MonitoringPoint(float latitude,float longitude,float temperature,float humidity,String img,String name){
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.humidity = humidity;
        this.img = img;
        this.name = name;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
