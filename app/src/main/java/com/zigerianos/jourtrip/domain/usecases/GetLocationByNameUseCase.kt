package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetLocationByNameUseCase(
    transformer: ObservableTransformer<List<Location>, List<Location>>,
    private val api: ApiService
) : UseCaseWithParams<GetLocationByNameUseCase.Params, List<Location>>(transformer) {

    override fun createObservable(params: Params): Observable<List<Location>> {
        return api
            .getLocationsByName(
                params.place,
                params.skip.toString(),
                params.limit.toString()
            ).map { it.data }
    }

    data class Params(
        val place: String,
        val skip: Int? = null,
        val limit: Int? = null
    )
}