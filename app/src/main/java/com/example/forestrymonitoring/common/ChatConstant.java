package com.example.forestrymonitoring.common;

import java.util.UUID;

/**
 * @Description: 常量
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/9/18 20:36.
 */
public class ChatConstant {

    /*UUID*/
    public static final UUID UUID_SECURE = UUID.fromString("5d0b9e95-d5fe-41d2-80af-375b45d2159f");
    public static final UUID UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    /*Message Type*/
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    /* MAC*/
    public static final String MEIZU_PRO_6S = "90:F0:52:08:B5:82";
    public static final String GNU = "C4:8E:8F:22:83:CE";
    public static final String GALAY_S4 = "E4:32:CB:B7:0C:FA";
    public static final String MI5 = "38:A4:ED:3C:FC:9C";
    public static final String Raspberry_Pi = "90:F0:52:08:B5:82";

    /*UUID*/
    // 蓝牙串口服务
    public static final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final UUID LANAccessUsingPPPServiceClass_UUID = UUID.fromString("00001102-0000-1000-8000-00805F9B34FB");
    // 拨号网络服务
    public static final UUID DialupNetworkingServiceClass_UUID = UUID.fromString("00001103-0000-1000-8000-00805F9B34FB");
    // 信息同步服务
    public static final UUID IrMCSyncServiceClass_UUID = UUID.fromString("00001104-0000-1000-8000-00805F9B34FB");
    public static final UUID SDP_OBEXObjectPushServiceClass_UUID = UUID.fromString("00001105-0000-1000-8000-00805F9B34FB");
    // 文件传输服务
    public static final UUID OBEXFileTransferServiceClass_UUID = UUID.fromString("00001106-0000-1000-8000-00805F9B34FB");
    public static final UUID IrMCSyncCommandServiceClass_UUID = UUID.fromString("00001107-0000-1000-8000-00805F9B34FB");
}
