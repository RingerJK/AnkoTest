package com.ringerjk.instamotortest

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by yurykanetski on 12/9/17.
 */

@Singleton
class ConnectionEventsDispatcher @Inject constructor() {
    private val subject: BehaviorSubject<ConnectionState> = BehaviorSubject.create()

    fun send(event: ConnectionState) {
        subject.onNext(event)
    }

    fun toObservable(): Observable<ConnectionState> {
        return subject
    }

    fun hasObservers(): Boolean {
        return subject.hasObservers()
    }
}

enum class ConnectionState {
    Available,
    Unavailable;
}
