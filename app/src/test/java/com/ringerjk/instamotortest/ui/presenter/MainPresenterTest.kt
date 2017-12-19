package com.ringerjk.instamotortest.ui.presenter

import android.util.Log
import com.ringerjk.instamotortest.ConnectionEventsDispatcher
import com.ringerjk.instamotortest.ConnectionState
import com.ringerjk.instamotortest.ui.MainPresenter
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.on
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


/**
 * Created by yurykanetski on 12/17/17.
 */

class MainPresenterTest : Spek({
    describe("Main Presenter") {

        on("Rx stream") {
            val connectionEventsDispatcher = ConnectionEventsDispatcher()
            val mainPresenter = MainPresenter(connectionEventsDispatcher)
            connectionEventsDispatcher.send(ConnectionState.Available)
            val observer = mainPresenter.combineEventsAndInternetConnectionObservable().test()
            observer.assertSubscribed()
            observer.await(5, TimeUnit.SECONDS)
            connectionEventsDispatcher.send(ConnectionState.Unavailable)
            val countsEvents = observer.valueCount()
            observer.await(5, TimeUnit.SECONDS)
            assertEquals(countsEvents, observer.valueCount())
            connectionEventsDispatcher.send(ConnectionState.Available)
            observer.await(5, TimeUnit.SECONDS)
            assertNotEquals(countsEvents, observer.valueCount())
        }

        on("Internet dispatcher") {
            val connectionEventsDispatcher = ConnectionEventsDispatcher()

            val observer = connectionEventsDispatcher.toObservable().test()
            assertTrue { observer.hasSubscription() }
            connectionEventsDispatcher.send(ConnectionState.Unavailable)
            assertEquals(observer.events[0][0], ConnectionState.Unavailable)
            connectionEventsDispatcher.send(ConnectionState.Unavailable)
            assertEquals(observer.events[0][1], ConnectionState.Unavailable)
            connectionEventsDispatcher.send(ConnectionState.Available)
            assertEquals(observer.events[0][2], ConnectionState.Available)
            observer.dispose()
        }
    }

}) {
}