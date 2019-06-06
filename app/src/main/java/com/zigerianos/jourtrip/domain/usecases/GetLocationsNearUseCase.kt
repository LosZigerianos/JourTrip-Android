package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetLocationsNearUseCase(
    transformer: ObservableTransformer<List<Location>, List<Location>>,
    private val api: ApiService
) : UseCaseWithParams<GetLocationsNearUseCase.Params, List<Location>>(transformer) {

    override fun createObservable(params: Params): Observable<List<Location>> {
        return api
            .getLocationsNear(
                params.latitude,
                params.longitude,
                params.skip.toString(),
                params.limit.toString(),
                params.search
            ).map { it.data }
    }

    data class Params(
        val latitude: String,
        val longitude: String,
        val skip: Int? = null,
        val limit: Int? = null,
        val search: String? = null
    )
}