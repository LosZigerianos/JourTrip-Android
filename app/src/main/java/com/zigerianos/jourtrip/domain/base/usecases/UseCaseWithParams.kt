package com.zigerianos.jourtrip.domain.base.usecases

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

abstract class UseCaseWithParams<in Params, T>(private val transformer: ObservableTransformer<T, T>) {

    protected abstract fun createObservable(params: Params): Observable<T>

    fun observable(params: Params): Observable<T> {
        return createObservable(params).compose(transformer)
    }
}
