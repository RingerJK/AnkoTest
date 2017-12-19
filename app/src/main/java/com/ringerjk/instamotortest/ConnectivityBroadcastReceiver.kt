package com.ringerjk.instamotortest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */

class ConnectivityBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var connectionChecker: ConnectionChecker
    @Inject lateinit var connectionEventsDispatcher: ConnectionEventsDispatcher

    override fun onReceive(context: Context?, intent: Intent?) {
        (context as BaseContext).appComponent.injectTo(this)

        if (connectionChecker.isOnline())
            connectionEventsDispatcher.send(ConnectionState.Available)
        else
            connectionEventsDispatcher.send(ConnectionState.Unavailable)
    }
}