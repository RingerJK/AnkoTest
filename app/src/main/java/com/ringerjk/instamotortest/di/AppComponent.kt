package com.ringerjk.instamotortest.di

import android.net.ConnectivityManager
import com.ringerjk.instamotortest.BaseContext
import com.ringerjk.instamotortest.ConnectionEventsDispatcher
import com.ringerjk.instamotortest.ConnectivityBroadcastReceiver
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, SystemServices::class))
interface AppComponent {

    fun getConnectivityManager(): ConnectivityManager
    fun getBaseContext(): BaseContext
    fun getConnectionEventsDispatcher(): ConnectionEventsDispatcher

    fun injectTo(connectivityReceiver: ConnectivityBroadcastReceiver)
}