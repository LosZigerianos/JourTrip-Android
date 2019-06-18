package com.zigerianos.jourtrip.domain.base.usecases

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

abstract class UseCase<T>(private val transformer: ObservableTransformer<T, T>) {

    protected abstract fun createObservable(): Observable<T>

    fun observable(): Observable<T> {
        return createObservable().compose(transformer)
    }
}
