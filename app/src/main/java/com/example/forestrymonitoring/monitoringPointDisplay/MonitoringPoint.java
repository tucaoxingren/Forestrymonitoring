package com.example.forestrymonitoring.monitoringPointDisplay;

/**
 * Created by 吐槽星人 on 2017/10/8 0008.
 * 封装监测点信息
 * 包括经纬度 温度 湿度 图片路径
 */

public class MonitoringPoint {

    private double longitude;//经度
    private double latitude;//纬度
    private double temperature;//温度
    private double humidity;//湿度
    private String img = null;//监测点图片

    public MonitoringPoint(){

    }
    public MonitoringPoint(double longitude,double latitude,double temperature,double humidity,String img){
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
        this.humidity = humidity;
        this.img = img;

    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
