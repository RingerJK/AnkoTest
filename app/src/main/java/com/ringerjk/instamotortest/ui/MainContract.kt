package com.ringerjk.instamotortest.ui

/**
 * Created by yurykanetski on 12/10/17.
 */
interface MainContract {
    interface View {
        var isInternetConnectionAvailable: Boolean

        fun setEvent(event: Int)
    }

    interface Presenter{

        fun attachView(v: MainContract.View)

        fun detachView()

        fun onCreate()

        fun onDestroy()
    }
}