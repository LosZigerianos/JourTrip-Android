package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.DataWithMeta
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.base.usecases.UseCase
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetUserMeUseCase(
    transformer: ObservableTransformer<User, User>,
    private val api: ApiService
)  : UseCaseWithParams<GetUserMeUseCase.Params, User>(transformer) {

    override fun createObservable(params: Params): Observable<User> {
        return api.getUserMeUseCase(params.userId).map { it.data }
    }

    data class Params(
        val userId: String
    )
}
