package com.ringerjk.instamotortest

import android.app.Application
import android.content.IntentFilter
import com.ringerjk.instamotortest.di.AppComponent
import com.ringerjk.instamotortest.di.AppModule
import com.ringerjk.instamotortest.di.DaggerAppComponent
import com.ringerjk.instamotortest.di.SystemServices

/**
 * Created by yurykanetski on 12/9/17.
 */
class BaseContext: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupConnectivityBr()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .systemServices(SystemServices(this))
                .build()

    }

    private fun setupConnectivityBr(){
        registerReceiver(ConnectivityBroadcastReceiver(),
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }
}