package com.example.forestrymonitoring.Adapter;

import android.content.Context;
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

    @Override
    public int getGroupCount() {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return 0;
        }
        return groupInfoList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return 0;
        }
        return groupInfoList.get(groupPosition).getBlueInfoList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return null;
        }
        return groupInfoList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupInfoList == null || groupInfoList.size() == 0) {
            return null;
        }
        return groupInfoList.get(groupPosition).getBlueInfoList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

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
        // 设置蓝牙信息
        BlueInfo BlueInfo = groupInfoList.get(groupPosition).getBlueInfoList().get(childPosition);
        bluetoothHolder.nameTv.setText(BlueInfo.getIdentificationName());
        bluetoothHolder.addressTv.setText(BlueInfo.getDeviceAddress());
        return convertView;
    }

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

    private static class GroupViewHolder{
        TextView nameTv;
    }
    private static class BluetoothHolder{
        ImageView iconIv;
        TextView nameTv;
        TextView addressTv;
    }
}
