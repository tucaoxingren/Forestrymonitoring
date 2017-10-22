package com.example.forestrymonitoring;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.view.View;
import android.widget.Toast;

import com.example.forestrymonitoring.Adapter.GroupExpandableListAdapter;
import com.example.forestrymonitoring.Adapter.onGroupExpandedListener;
import com.example.forestrymonitoring.bluetoothCommunication.BluetoothManager;
import com.example.forestrymonitoring.mode.AboutInfo;
import com.example.forestrymonitoring.mode.GroupInfo;

import java.util.ArrayList;
import java.util.List;

public class BluetoothActivity extends BaseActivity {

    // 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    private static final String TAG = "NormalExpandActivity";
    private Context mContext;
    private List<GroupInfo> groupInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        // 初始化
        mContext = this;
        final ExpandableListView listView = (ExpandableListView) findViewById(R.id.friend_group_list);
        // 获取分组及各组数据
        groupInfoList.add(BluetoothManager.findPareDevice(mContext));// 已配对
        groupInfoList.add(BluetoothManager.findUnPareDevice(mContext));// 未配对
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
        if (id == R.id.menu_about) {
            AboutInfo.displayAboutDialog(mContext);// 关于
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
}
