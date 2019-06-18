package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostRecoverPasswordByEmailUseCase(
    transformer: ObservableTransformer<String, String>,
    private val api: ApiService
) : UseCaseWithParams<PostRecoverPasswordByEmailUseCase.Params, String>(transformer) {

    override fun createObservable(params: Params): Observable<String> {
        return api.postRecoverPasswordUseCase(params.userRequest).map { it.message }
    }

    data class Params(val userRequest: UserRequest)
}