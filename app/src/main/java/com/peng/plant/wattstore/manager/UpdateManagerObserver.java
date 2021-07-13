package com.peng.plant.wattstore.manager;

public interface UpdateManagerObserver {
    void onDownloadComplete(String path);
    void onDownloadFailed();
    void onCheckPowerStoreVersion(String updateVersion);
    void onDownloadProgressBarStart();
    void onDownloadProgressBarStop();
    void onDownloadProgressBarCancel();
}
