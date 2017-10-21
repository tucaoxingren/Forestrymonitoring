package com.example.forestrymonitoring.common;

import java.util.UUID;

/**
 * @Description: 常量
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/9/18 20:36.
 */
public class ChatConstant {

    //Start flag
    public static final byte VISE_COMMAND_START_FLAG = (byte) 0xFF;
    //Protocol version
    public static final byte VISE_COMMAND_PROTOCOL_VERSION = (byte) 0x01;

    /*Send Command Type*/
    public static final byte VISE_COMMAND_TYPE_NONE = (byte) 0x00;
    public static final byte VISE_COMMAND_TYPE_TEXT = (byte) 0x01;
    public static final byte VISE_COMMAND_TYPE_FILE = (byte) 0x02;
    public static final byte VISE_COMMAND_TYPE_IMAGE = (byte) 0x03;
    public static final byte VISE_COMMAND_TYPE_AUDIO = (byte) 0x04;
    public static final byte VISE_COMMAND_TYPE_VIDEO = (byte) 0x05;

    /*KEY*/
    public static final String NAME_SECURE = "BluetoothChatSecure";
    public static final String NAME_INSECURE = "BluetoothChatInsecure";

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
}
