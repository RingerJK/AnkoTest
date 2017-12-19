package com.ringerjk.instamotortest.di

import com.ringerjk.instamotortest.ui.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by yurykanetski on 12/9/17.
 */

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides
    @ActivityScope
    fun provideMainActivity(): MainActivity = activity


}