package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostSignupUseCase(
    transformer: ObservableTransformer<User, User>,
    private val api: ApiService
) : UseCaseWithParams<PostSignupUseCase.Params, User>(transformer) {

    override fun createObservable(params: Params): Observable<User> {
        return api.postSingupUseCase(params.userRequest).map { it.data }
    }

    data class Params(val userRequest: UserRequest)
}