package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PutUserDataUseCase(
    transformer: ObservableTransformer<Boolean, Boolean>,
    private val api: ApiService
) : UseCaseWithParams<PutUserDataUseCase.Params, Boolean>(transformer) {

    override fun createObservable(params: Params): Observable<Boolean> {
        return api.putUserDataUseCase(params.userRequest).map { it.success }
    }

    data class Params(val userRequest: UserRequest)
}