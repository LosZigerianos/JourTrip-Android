package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.DataWithMeta
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostLoginUseCase(
    transformer: ObservableTransformer<DataWithMeta<String, User>, DataWithMeta<String, User>>,
    private val api: ApiService
) : UseCaseWithParams<PostLoginUseCase.Params, DataWithMeta<String, User>>(transformer) {

    override fun createObservable(params: Params): Observable<DataWithMeta<String, User>> {
        return api.postLoginUseCase(params.userRequest).map { DataWithMeta(data = it.data, metadata = it.metadata, success = it.success) }
    }

    data class Params(val userRequest: UserRequest)
}