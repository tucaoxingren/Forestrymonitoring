package com.example.forestrymonitoring;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    // 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    // Bluetooth 设备可见时间，单位：秒。
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;

    Button blueButton = null;
    TextView blueAddressText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        blueButton = (Button)findViewById(R.id.blueButton);
        blueAddressText = (TextView)findViewById(R.id.blueAddressTextView);
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //得到BluetoothAdapter对象
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if(adapter != null){
                    System.out.println("本机拥有蓝牙设备");
                    // 判断蓝牙设备是否打开
                    if(!adapter.isEnabled()){//如果蓝牙没有打开
                        //调用系统打开蓝牙的activity
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        BluetoothActivity.this.startActivity(intent);
                        // 设置请求权限的提示内容 可以不设置
                        //turnOnBluetooth();
                    }
                    // 得到所有的已经配对的蓝牙适配器对象
                    Set<BluetoothDevice> devices = adapter.getBondedDevices();
                    StringBuffer blueAddress = null;
                    if (devices.size() > 0){
                        blueAddress = new StringBuffer("已配对蓝牙设备地址：\n");
                        for (Iterator iterator = devices.iterator(); iterator.hasNext();){
                            BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                            // 得到远程蓝牙设备的地址
                            System.out.println(bluetoothDevice.getAddress());
                            blueAddress.append(bluetoothDevice.getName()+" : "+bluetoothDevice.getAddress()+"\n");
                        }
                        blueAddressText.setText(blueAddress);
                    }
                } else {
                    System.out.println("本机没有蓝牙设备");
                    blueAddressText.setText("本机没有蓝牙设备");
                }
            }
        });
    }

    /**
     * 弹出系统弹框提示用户打开 Bluetooth
     */
    private void turnOnBluetooth()
    {
        // 请求打开 Bluetooth
        Intent requestBluetoothOn = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);

        // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
        requestBluetoothOn
                .setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // 设置 Bluetooth 设备可见时间
        requestBluetoothOn.putExtra(
                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                BLUETOOTH_DISCOVERABLE_DURATION);

        // 请求开启 Bluetooth
        this.startActivityForResult(requestBluetoothOn,
                REQUEST_CODE_BLUETOOTH_ON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON)
        {
            switch (resultCode)
            {
                // 点击确认按钮
                case Activity.RESULT_OK:
                {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启
                }
                break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED:
                {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                }
                break;
                default:
                    break;
            }
        }
    }
}
