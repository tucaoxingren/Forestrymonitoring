package com.example.forestrymonitoring;

import android.app.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.ExpandableListView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forestrymonitoring.Adapter.GroupExpandableListAdapter;
import com.example.forestrymonitoring.Adapter.onGroupExpandedListener;
import com.example.forestrymonitoring.mode.BlueInfo;
import com.example.forestrymonitoring.mode.GroupInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends BaseActivity {

    // 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    // Bluetooth 设备可见时间，单位：秒。
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;

    private static final String TAG = "NormalExpandActivity";
    public Context mContext;
    private List<GroupInfo> groupInfoList = new ArrayList<>();
    private ImageView blueImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        // 初始化
        mContext = this;
        final ExpandableListView listView = (ExpandableListView) findViewById(R.id.friend_group_list);
        // 获取分组及各组数据
        groupInfoList.add(findPareDevice());
        groupInfoList.add(findUnPareDevice());
        // 构建 adapter
        GroupExpandableListAdapter adapter = new GroupExpandableListAdapter(mContext,groupInfoList);
        // adapter点击监听
        adapter.setOnGroupExpandedListener(new onGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition) {
                expandOnlyOne(listView, groupPosition, groupInfoList.size());
            }
        });
        // 根据adapter 绘制 listView
        listView.setAdapter(adapter);
        //  设置分组项的点击监听事件
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(TAG, "onGroupClick: groupPosition:" + groupPosition + ", id:" + id);
                // 请务必返回 false，否则分组不会展开
                return false;
            }
        });
        //  设置子选项点击监听事件
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(BluetoothActivity.this, "点击连接暂未实现", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    // 每次展开一个分组后，关闭其他的分组
    private boolean expandOnlyOne(ExpandableListView view, int expandedPosition, int groupLength) {
        boolean result = true;
        for (int i = 0; i < groupLength; i++) {
            if (i != expandedPosition && view.isGroupExpanded(i)) {
                result &= view.collapseGroup(i);
            }
        }
        return result;
    }

    /**
     * 查找已适配蓝牙
     * @return 已适配蓝牙列表
     */
    private GroupInfo findPareDevice(){
        //得到BluetoothAdapter对象
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        List<BlueInfo> blueInfoList = new ArrayList<>();
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
            if (devices.size() > 0){
                //blueAddress = new StringBuffer("已配对蓝牙设备地址：\n");
                for (Iterator iterator = devices.iterator(); iterator.hasNext();){
                    BlueInfo blueInfo = new BlueInfo();
                    // 得到远程蓝牙设备
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                    blueInfo.setIdentificationName(bluetoothDevice.getName());
                    blueInfo.setDeviceAddress(bluetoothDevice.getAddress());
                    blueInfo.setBluetoothDevice(bluetoothDevice);
                    // 加入蓝牙列表
                    blueInfoList.add(blueInfo);
                }
            }
        } else {
            Log.e("NoBluetooth", "本机没有蓝牙设备");
            Toast.makeText(BluetoothActivity.this,"本机没有蓝牙设备",Toast.LENGTH_SHORT).show();
        }
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName("已配对蓝牙列表");
        groupInfo.setBlueInfoList(blueInfoList);
        return groupInfo;
    }


    /**
     *  未适配蓝牙
     * @return 未适配蓝牙列表
     */
    private GroupInfo findUnPareDevice(){
        GroupInfo groupInfo = new GroupInfo();
        List<BlueInfo> blueInfoList = new ArrayList<>();
        BlueInfo blueInfo = new BlueInfo();
        blueInfo.setIdentificationName("暂未实现");
        blueInfo.setDeviceAddress("FF:FF:FF:FF:FF:FF");
        blueInfoList.add(blueInfo);
        groupInfo.setBlueInfoList(blueInfoList);
        groupInfo.setGroupName("未配对蓝牙列表");
        return groupInfo;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用menu布局文件构建menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {// 关于
            displayAboutDialog();
            return true;
        } else if(id == R.id.menu_bluetooth){//蓝牙信息
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(BluetoothActivity.this,BluetoothActivity.class);
            BluetoothActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_bluetoothTest){//蓝牙测试
            //生成一个Intent对象
            Intent intent = new Intent();
            intent.setClass(BluetoothActivity.this,BlueTestActivity.class);
            BluetoothActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_exit){// 退出
//            finish();
            AtyContainer.getInstance().finishAllActivity();
            return true;
        }
        else if(id == R.id.menu_main){// 监测界面
            Intent intent = new Intent();
            intent.setClass(BluetoothActivity.this,MapActivity.class);
            BluetoothActivity.this.startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_home){// 首页
            Intent intent = new Intent();
            intent.setClass(BluetoothActivity.this,MainActivity.class);
            BluetoothActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // 绘制关于界面Dialog
    private void displayAboutDialog() {
        final int paddingSizeDp = 5;
        final float scale = getResources().getDisplayMetrics().density;
        final int dpAsPixels = (int) (paddingSizeDp * scale + 0.5f);

        final TextView textView = new TextView(this);
        final SpannableString text = new SpannableString(getString(R.string.aboutText));

        textView.setText(text);
        textView.setAutoLinkMask(RESULT_OK);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        Linkify.addLinks(text, Linkify.ALL);
        new AlertDialog.Builder(this)//AlertDialog
                .setTitle(R.string.menu_about)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .setView(textView)
                .show();
    }
}
