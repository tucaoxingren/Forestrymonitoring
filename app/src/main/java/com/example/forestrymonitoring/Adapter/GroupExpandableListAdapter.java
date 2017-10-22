package com.example.forestrymonitoring.Adapter;

import android.content.Context;
import android.provider.Settings;
import android.widget.BaseExpandableListAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forestrymonitoring.R;
import com.example.forestrymonitoring.mode.BlueInfo;
import com.example.forestrymonitoring.mode.GroupInfo;

import java.util.List;

/**
 * Created by 吐槽星人 on 2017/10/21 0021.
 *  ExpandableListView  匹配类
 */

public class GroupExpandableListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "NormalExpandableListAda";
    private onGroupExpandedListener mOnGroupExpandedListener;
    private Context context;
    private List<GroupInfo> groupInfoList;

    public GroupExpandableListAdapter(Context context,List<GroupInfo> groupInfoList) {
        this.context = context;
        this.groupInfoList = groupInfoList;
    }
    // 监听方法
    public void setOnGroupExpandedListener(onGroupExpandedListener onGroupExpandedListener) {
        mOnGroupExpandedListener = onGroupExpandedListener;
    }

    //        获取分组的个数
    @Override
    public int getGroupCount() {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return 0;
        }
        return groupInfoList.size();
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return 0;
        }
        return groupInfoList.get(groupPosition).getBlueInfoList().size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return null;
        }
        return groupInfoList.get(groupPosition);
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return null;
        }
        return groupInfoList.get(groupPosition).getBlueInfoList().get(childPosition);
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_group_normal, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.nameTv = (TextView) convertView.findViewById(R.id.label_group_normal);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        // 设置分组名
        groupViewHolder.nameTv.setText(groupInfoList.get(groupPosition).getGroupName());
        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        BluetoothHolder bluetoothHolder;
        if (convertView == null) {
            // 加载布局文件
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_info, null);
            bluetoothHolder = new BluetoothHolder();
            bluetoothHolder.iconIv = (ImageView) convertView.findViewById(R.id.item_friend_icon);
            bluetoothHolder.nameTv = (TextView) convertView.findViewById(R.id.item_friend_name);
            bluetoothHolder.addressTv = (TextView) convertView.findViewById(R.id.item_friend_address);
            convertView.setTag(bluetoothHolder);
        } else {
            bluetoothHolder = (BluetoothHolder) convertView.getTag();
        }
        // 设置蓝牙信息展示view
        BlueInfo BlueInfo = groupInfoList.get(groupPosition).getBlueInfoList().get(childPosition);
        bluetoothHolder.nameTv.setText(BlueInfo.getIdentificationName());
        bluetoothHolder.addressTv.setText(BlueInfo.getDeviceAddress());
        int resId = findImgbyDevice(BlueInfo.getIdentificationName(),BlueInfo.getDeviceAddress());
        bluetoothHolder.iconIv.setImageResource(resId);

        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Log.d(TAG, "onGroupExpanded() called with: groupPosition = [" + groupPosition + "]");
        if (mOnGroupExpandedListener != null) {
            mOnGroupExpandedListener.onGroupExpanded(groupPosition);
        }
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Log.d(TAG, "onGroupCollapsed() called with: groupPosition = [" + groupPosition + "]");
    }

    private int findImgbyDevice(String brand,String Mac){
//        String brand = android.os.Build.BRAND;
        String[] temp = brand.split(" ");
        brand = temp[0];
        String[] temp2 = Mac.split(":");
        Mac = temp2[0];
        int resId;
        System.out.println(brand);
        if (brand.equals("XiaoMi"))
            resId = R.mipmap.xiaomi_mi_453px;
        else if (brand.equals("MEIZU"))
            resId = R.mipmap.meizu;
        else if (brand.equals("GNU"))
            resId = R.mipmap.acer_512px;
        else if (brand.equals("SAMSUNG"))
            resId = R.mipmap.samsung_475px;
        else if (Mac.equals("E4"))
            resId = R.mipmap.samsung_475px;
        else if (Mac.equals("30"))
            resId = R.mipmap.linux_91px;
        else// if (Mac.equals("FF"))
            resId = R.mipmap.raspberry_438px;
        return resId;
    }

    private static class GroupViewHolder{
        TextView nameTv;
    }
    private static class BluetoothHolder{
        ImageView iconIv;
        TextView nameTv;
        TextView addressTv;
    }
}
