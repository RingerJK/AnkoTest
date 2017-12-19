package com.ringerjk.instamotortest.di

import com.ringerjk.instamotortest.ui.MainActivity
import com.ringerjk.instamotortest.ui.MainPresenter
import dagger.Component
import dagger.Subcomponent

/**
 * Created by yurykanetski on 12/9/17.
 */

@ActivityScope
@Component(modules = arrayOf(ActivityModule::class), dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}