package com.zigerianos.jourtrip.domain.base.usecases

import io.reactivex.Completable
import io.reactivex.CompletableTransformer

abstract class CompletableUseCaseWithParams<in Params>(private val transformer: CompletableTransformer) {

    protected abstract fun createCompletable(params: Params): Completable

    fun completable(params: Params): Completable {
        return createCompletable(params).compose(transformer)
    }
}
