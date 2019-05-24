package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.base.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetUserMeUseCase(
    transformer: ObservableTransformer<User, User>,
    private val api: ApiService
) : UseCase<User>(transformer) {
    override fun createObservable(): Observable<User> =
        api.getUserMeUseCase().map { it.data }
}