package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.TokenResponse
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetTokenValidationUseCase(
    transformer: ObservableTransformer<TokenResponse, TokenResponse>,
    private val api: ApiService
) : UseCaseWithParams<GetTokenValidationUseCase.Params, TokenResponse>(transformer) {

    override fun createObservable(params: Params): Observable<TokenResponse> {
        return api.getTokenValidationUseCase(params.token).map { it }
    }

    data class Params(
        val token: String? = null
    )
}