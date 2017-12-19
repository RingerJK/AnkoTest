package com.ringerjk.instamotortest.di

import android.content.Context
import android.net.ConnectivityManager
import com.ringerjk.instamotortest.BaseContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */

@Module
class SystemServices(private val baseContext: BaseContext) {

    @Provides
    @Singleton
    fun connectivityManager(): ConnectivityManager {
        return baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


}