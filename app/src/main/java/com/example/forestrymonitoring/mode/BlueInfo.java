package com.example.forestrymonitoring.mode;

import android.bluetooth.BluetoothDevice;

/**
 * Created by 吐槽星人 on 2017/10/21 0021.
 */

public class BlueInfo {
    private String deviceAddress;//设备地址
    private String identificationName;//标识名称，即设备名称
    private BluetoothDevice bluetoothDevice;

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getIdentificationName() {
        return identificationName;
    }
    public void setIdentificationName(String identificationName) {
        this.identificationName = identificationName;
    }



}
