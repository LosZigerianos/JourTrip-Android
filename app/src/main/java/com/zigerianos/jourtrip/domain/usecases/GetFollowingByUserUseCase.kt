package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetFollowingByUserUseCase(
    transformer: ObservableTransformer<List<User>, List<User>>,
    private val api: ApiService
) : UseCaseWithParams<GetFollowingByUserUseCase.Params, List<User>>(transformer) {

    override fun createObservable(params: Params): Observable<List<User>> {
        return api
            .getFollowingByUserUseCase(
                params.userId,
                params.skip.toString(),
                params.limit.toString()
            ).map { it.data }
    }

    data class Params(
        val userId: String,
        val skip: Int? = null,
        val limit: Int? = null
    )
}