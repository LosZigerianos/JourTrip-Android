package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.DataWithMeta
import com.zigerianos.jourtrip.data.entities.FollowingRequest
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostAddFollowingUseCase(
    transformer: ObservableTransformer<DataWithMeta<User, User>, DataWithMeta<User, User>>,
    private val api: ApiService
) : UseCaseWithParams<PostAddFollowingUseCase.Params, DataWithMeta<User, User>>(transformer) {

    override fun createObservable(params: Params): Observable<DataWithMeta<User, User>> {
        return api.postAddFollowingUseCase(params.userId, params.userRequest)
            .map { DataWithMeta(data = it.data, metadata = it.metadata, success = it.success) }
    }

    data class Params(
        val userId: String,
        val userRequest: FollowingRequest
    )
}