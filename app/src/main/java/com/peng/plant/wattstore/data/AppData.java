package com.peng.plant.wattstore.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

//json 데이터 임시저장
public class AppData {

    public int position = 0;
    public int orderCount = 0;
    public String appNameKor = "";
    public String appNameEng = "";
    public String appPackageName = "";
    //    public int license = -1; // -1:not installable 0: Unavailable 1~: available
    public String appDetailJson = "";
    public int useOnGlass = 0;
    public int useOnPC = 0;
    //    public String signalingUrl = "";
//    public String relayUrlV4 = "";
//    public String relayUrlV6 = "";
//    public String pushUrl = "";
//    public String signalingVersion = "";
    public String appCurrentVersion = "";
    public String appUpdateVersion = "";
    //    public String webVersion = "";
    public boolean isInstalled = false; // here
    public int isNewVersion  = 0; // here
    public String fileName = ""; // here

    public Object getAppDetailJson(int appCode)
    {
        Object resultData = null;
        try
        {
            JSONObject json = new JSONObject(appDetailJson);
            switch (appCode)
            {
                case 1: // powertalk
                    AppPowerTalkData data = new AppPowerTalkData();
                    data.serverURL01 = json.getString("serverUrl01");
                    data.serverURL02 = json.getString("serverUrl02");
                    data.serverURL03 = json.getString("serverUrl03");
                    data.serverURL04 = json.getString("serverUrl04");
                    resultData = data;
                    break;
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return resultData;
    }

    public String getAppName()
    {
        return Locale.getDefault().getLanguage().contains("ko") ? appNameKor : appNameEng;
    }


//    public String setFileName(String nameEng)
//    {
//        return String.format("%s_%s", nameEng , appUpdateVersion);
//    }
}
