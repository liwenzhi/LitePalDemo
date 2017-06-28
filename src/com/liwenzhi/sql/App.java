package com.liwenzhi.sql;

import android.app.Application;
import org.litepal.LitePal;

/**
 *
 */
public class App extends Application {
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        LitePal.initialize(this);
    }
}
