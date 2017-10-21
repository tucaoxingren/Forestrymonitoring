package com.example.forestrymonitoring.mode;

import java.util.List;

/**
 * Created by 吐槽星人 on 2017/10/21 0021.
 */

public class GroupInfo {
    private String groupName;//名称
    private List<BlueInfo> blueInfoList;//该组好友列表

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<BlueInfo> getBlueInfoList() {
        return blueInfoList;
    }

    public void setBlueInfoList(List<BlueInfo> blueInfoList) {
        this.blueInfoList = blueInfoList;
    }
}
