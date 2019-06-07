package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetTimeLineUseCase(
    transformer: ObservableTransformer<Data<List<Comment>>, Data<List<Comment>>>,
    private val api: ApiService
) : UseCaseWithParams<GetTimeLineUseCase.Params, Data<List<Comment>>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<List<Comment>>> {
        return api.getTimeLineUseCase(params.userId, params.skip.toString(), params.limit.toString()).map { it }
    }

    data class Params(
        val userId: String,
        val skip: Int? = null,
        val limit: Int? = null
    )
}