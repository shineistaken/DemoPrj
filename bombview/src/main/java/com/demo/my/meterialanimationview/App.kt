package com.demo.my.meterialanimationview

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by lenovo on 2018/3/12.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}