package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.PasswordRequest
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PutPasswordUseCase(
    transformer: ObservableTransformer<Boolean, Boolean>,
    private val api: ApiService
) : UseCaseWithParams<PutPasswordUseCase.Params, Boolean>(transformer) {

    override fun createObservable(params: Params): Observable<Boolean> {
        return api.putPasswordUseCase(params.passwordRequest).map { it.success }
    }

    data class Params(val passwordRequest: PasswordRequest)
}