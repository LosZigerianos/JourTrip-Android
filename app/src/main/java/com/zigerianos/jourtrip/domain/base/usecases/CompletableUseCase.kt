package com.zigerianos.jourtrip.domain.base.usecases

import io.reactivex.Completable
import io.reactivex.CompletableTransformer

abstract class CompletableUseCase(private val transformer: CompletableTransformer) {

    protected abstract fun createCompletable(): Completable

    fun completable(): Completable {
        return createCompletable().compose(transformer)
    }
}
