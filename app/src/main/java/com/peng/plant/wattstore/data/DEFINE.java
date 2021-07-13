package com.peng.plant.wattstore.data;


import android.os.Environment;

//서버 주소 정의
public class DEFINE {

    public static final String EVENT_MSG_DEVICE_LOGIN = "deviceLogin";
    public static final String EVENT_MSG_DEVICE_REGISTER = "deviceRegistration";

    public static final String EVENT_MSG_GETAPPLISTS = "getAppLists";
    public static final String EVENT_MSG_SOCKET_CONNECTED = "socketconnected";
    public static final String EVENT_MSG_USER_PATTERN = "userPattern";
    public static final String EVENT_MSG_USER_LOGIN = "userLogin";
    public static final String EVENT_MSG_USER_LOGOUT = "userLogout";
    public static final String EVENT_MSG_USER_REGISTER = "userRegistration";
    public static final String EVENT_MSG_USER_LOGOUT_FORCE = "forceLogout";

    public static final String APP_PACKAGE_NAME_SETTINGS = "com.android.settings";
    public static final String APP_PACKAGE_NAME_CAMERA = "com.realwear.camera";
    public static final String APP_PACKAGE_NAME_FILEBROWSER = "com.realwear.filebrowser";

//    public static final boolean isVPNOn = true;
//    public static final String POWERSTORE_SERVER_ADDRESS = "https://172.50.22.86";
//    public static final String POWERSTORE_SERVER_PORT = "8500";
//    public static final String POWERSTORE_SERVER_DOWNLOAD_ADDRESS = "https://cmss.komipo.co.kr"; // 중부발전 건설현장
//    private static final String SERVER_URL = POWERSTORE_SERVER_DOWNLOAD_ADDRESS + "/"+ "powerstore" + "/";

//    public static final boolean isVPNOn = true;
//    public static final String POWERSTORE_SERVER_ADDRESS = "https://172.50.22.70";
//    public static final String POWERSTORE_SERVER_PORT = "8002";
//    public static final String POWERSTORE_SERVER_DOWNLOAD_ADDRESS = "https://arsystem.komipo.co.kr"; // 중부발전 위급상황
//    private static final String SERVER_URL = POWERSTORE_SERVER_DOWNLOAD_ADDRESS + "/"+ "powerstore" + "/";

//    public static final boolean isVPNOn = false;
//    public static final String POWERSTORE_SERVER_ADDRESS = "https://smartg.powertalk.kr"; // smartg
//    public static final String POWERSTORE_SERVER_PORT = "8302";
//    private static final String SERVER_URL = POWERSTORE_SERVER_ADDRESS+ ":1443/" + "powerstore" + "/"; // SMARTG 만 해당

    public static final boolean isVPNOn = false;
    public static final String POWERSTORE_SERVER_ADDRESS = "https://wattcloud.powertalk.kr"; // watt
    public static final String POWERSTORE_SERVER_PORT = "8352";
    private static final String SERVER_URL = "https://watt.powertalk.kr" + ":8357/" + "powerstore" + "/";

//    public static final boolean isVPNOn = false;
//    public static final String POWERSTORE_SERVER_ADDRESS = "https://106.10.33.94"; // meet
//    public static final String POWERSTORE_SERVER_PORT = "8333";
//    public static final String POWERSTORE_SERVER_DOWNLOAD_ADDRESS = "https://meet.powertalk.kr:8322";
//    private static final String SERVER_URL = POWERSTORE_SERVER_DOWNLOAD_ADDRESS + "/"+ "powerstore" + "/";

//    public static final boolean isVPNOn = false;
//    public static final String POWERSTORE_SERVER_ADDRESS = "https://powermanager.powertalk.co.kr";
//    public static final String POWERSTORE_SERVER_PORT = "8752";
//    private static final String SERVER_URL = POWERSTORE_SERVER_ADDRESS+ ":8758/" + "powerstore" + "/";

    public static final String LOCAL_DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";

//    private static final String SERVER_URL = POWERSTORE_SERVER_ADDRESS+ "/" + "powerstore" + "/"; // 기본
//    private static final String SERVER_URL = POWERSTORE_SERVER_ADDRESS+ ":8443/" + "powerstore" + "/"; // 삼성엔지니어링 만 해당
//private static final String SERVER_URL = POWERSTORE_SERVER_ADDRESS+ "/" + "powerstore" + "/"; // kpjb

    public static final String SERVER_APP_DOWNLOAD_URL = SERVER_URL + "apps" + "/";
    public static final String SERVER_LOGO_DOWNLOAD_URL = SERVER_URL + "icons" + "/";
}
//"https://watt.powertalk.kr" + ":8357/" + "powerstore" + "/"+"icons"+"/";