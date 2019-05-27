package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.UserProfile
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetUserProfileUseCase(
    transformer: ObservableTransformer<UserProfile, UserProfile>,
    private val api: ApiService
) : UseCaseWithParams<GetUserProfileUseCase.Params, UserProfile>(transformer) {

    override fun createObservable(params: Params): Observable<UserProfile> {
        return api.getUserProfileUseCase(params.userId).map { it.data }
    }

    data class Params(
        val userId: String
    )
}
