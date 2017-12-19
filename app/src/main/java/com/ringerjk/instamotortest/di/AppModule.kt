package com.ringerjk.instamotortest.di

import com.ringerjk.instamotortest.BaseContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */
@Module
class AppModule(private val baseContext: BaseContext) {

    @Provides
    @Singleton
    fun baseContext(): BaseContext {
        return baseContext
    }
}