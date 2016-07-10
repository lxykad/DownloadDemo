package com.lxy.download;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by liuxinyu on 2016/7/10.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }
}
