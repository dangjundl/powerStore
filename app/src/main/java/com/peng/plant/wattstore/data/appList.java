package com.peng.plant.wattstore.data;

import java.util.concurrent.ConcurrentHashMap;

public class appList {

    public ConcurrentHashMap<Integer , AppData> get()
    {
        ConcurrentHashMap<Integer , AppData> apps = new ConcurrentHashMap<>();

        apps.put(0 , getCameraData(0));
        apps.put(1 , getMyFolder(1));
        apps.put(2 , getSettings(2));
        return apps;
    }

    public ConcurrentHashMap<Integer , AppData> get2()
    {
        ConcurrentHashMap<Integer , AppData> apps = new ConcurrentHashMap<>();

        apps.put(0 , getSettings(0));
        apps.put(1 , getSWUpdate(1));
        return apps;
    }

    private AppData getCameraData(int pos)
    {
        AppData data = new AppData();
        data.position = pos;
        data.appNameKor = "내카메라";
        data.appNameEng = "camera";
        data.appPackageName = "com.realwear.camera";
        return data;
    }

    private AppData getMyFolder(int pos)
    {
        AppData data = new AppData();
        data.position = pos;
        data.appNameKor = "내문서";
        data.appNameEng = "myfolder";
        data.appPackageName = "com.realwear.filebrowser";
        return data;
    }

    private AppData getSettings(int pos)
    {
        AppData data = new AppData();
        data.position = pos;
        data.appNameKor = "설정";
        data.appNameEng = "settings";
        data.appPackageName = "com.android.settings";
        return data;
    }

    private AppData getSWUpdate(int pos)
    {
        AppData data = new AppData();
        data.position = pos;
        data.appNameKor = "시스템 업데이트";
        data.appNameEng = "system update";
        data.appPackageName = "com.realwear.fota";
        return data;
    }

}