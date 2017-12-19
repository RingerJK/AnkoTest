package com.ringerjk.instamotortest.ui

import android.util.Log
import com.ringerjk.instamotortest.ConnectionEventsDispatcher
import com.ringerjk.instamotortest.ConnectionState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by yurykanetski on 12/9/17.
 */

class MainPresenter @Inject constructor(private val connectionEventsDispatcher: ConnectionEventsDispatcher) :
        MainContract.Presenter {
    private val TAG = this::class.java.simpleName
    private var viewHolder: WeakReference<MainContract.View>? = null
    private val view: MainContract.View?
        get() = viewHolder?.get()
    private val compositeDisposable = CompositeDisposable()

    override fun attachView(v: MainContract.View) {
        viewHolder = WeakReference(v)
    }

    override fun detachView() {
        viewHolder?.clear()
        viewHolder = null
    }


    override fun onCreate() {
        subscribeInternetConnection()
        subscribeEvents()
        setupInternetConnectionListener()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    private fun setupInternetConnectionListener() {
        internetConnectionObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.isInternetConnectionAvailable = it == ConnectionState.Available
                }
                .addTo(compositeDisposable)
    }

    private fun internetConnectionObservable(): Observable<ConnectionState> =
            connectionEventsDispatcher.toObservable()

    private fun eventsObservable(): Observable<Long> =
            Observable.interval(1, TimeUnit.SECONDS)

    private fun subscribeInternetConnection() {
        internetConnectionObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.isInternetConnectionAvailable = it == ConnectionState.Available
                })
                .addTo(compositeDisposable)
    }

    fun combineEventsAndInternetConnectionObservable(): Observable<Long> {
        return Observable.combineLatest(internetConnectionObservable(), eventsObservable(),
                BiFunction<ConnectionState, Long, Long> { connectionState, event ->
                    if (connectionState == ConnectionState.Unavailable)
                        throw IOException("Connection has been interrupted")

//                    Log.d(TAG, "combineLatest event = $event conn = $connectionState")
                    return@BiFunction event
                })
                .retryWhen {
                    it.flatMap {
//                        Log.e(TAG, "RetryWhen exc: ${it.message}", it)
                        return@flatMap if (it is IOException)
                            Observable.just(1).timeout(5, TimeUnit.SECONDS)
                        else
                            Observable.error(it)
                    }
                }
                .subscribeOn(Schedulers.io())
    }

    private fun subscribeEvents() {
        combineEventsAndInternetConnectionObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "$it")
                    view?.setEvent(it.toInt())
                }, {
                    Log.e(TAG, it.message, it)
                })
                .addTo(compositeDisposable)

    }
}