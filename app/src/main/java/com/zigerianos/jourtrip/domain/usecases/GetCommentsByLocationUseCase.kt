package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetCommentsByLocationUseCase(
    transformer: ObservableTransformer<List<Comment>, List<Comment>>,
    private val api: ApiService
) : UseCaseWithParams<GetCommentsByLocationUseCase.Params, List<Comment>>(transformer) {

    override fun createObservable(params: Params): Observable<List<Comment>> {
        return api
            .getCommentsByLocationUseCase(
                params.locationId,
                params.skip.toString(),
                params.limit.toString()
            ).map { it.data }
    }

    data class Params(
        val locationId: String,
        val skip: Int? = null,
        val limit: Int? = null
    )
}
