package com.ringerjk.instamotortest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.ringerjk.instamotortest.BaseContext
import com.ringerjk.instamotortest.di.DaggerActivityComponent
import org.jetbrains.anko.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject lateinit var presenter: MainPresenter
    private lateinit var ui: MainActivityUi
    override var isInternetConnectionAvailable: Boolean = false
        set(value) {
            if (value)
                ui.setInternetConnectionStatus("Online")
            else
                ui.setInternetConnectionStatus("Offline")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerActivityComponent
                .builder()
                .appComponent((applicationContext as BaseContext).appComponent)
                .build()
                .inject(this)

        ui = MainActivityUi(presenter).also { it.setContentView(this) }

        presenter.attachView(this)
        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        presenter.detachView()
        super.onDestroy()
    }

    override fun setEvent(event: Int) {
        ui.setEvent(event)
    }
}

class MainActivityUi(presente: MainContract.Presenter) : AnkoComponent<MainActivity> {
    fun setEvent(event: Int) {
        this.event.text = event.toString()
    }

    fun setInternetConnectionStatus(status: String) {
        internetConnectionStatus.text = status
    }

    private lateinit var event: TextView
    private lateinit var internetConnectionStatus: TextView

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            padding = dip(24)
            linearLayout {
                textView("Event: ")
                event = textView {
                    textSize = 16f
                }.lparams {

                }

            }
            linearLayout {
                textView("Internet connection status: ")
                internetConnectionStatus = textView()
            }
        }

    }

}
