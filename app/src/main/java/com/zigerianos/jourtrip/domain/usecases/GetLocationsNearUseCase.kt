package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetLocationsNearUseCase(
    transformer: ObservableTransformer<Data<List<Location>>, Data<List<Location>>>,
    private val api: ApiService
) : UseCaseWithParams<GetLocationsNearUseCase.Params, Data<List<Location>>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<List<Location>>> {
        return api
            .getLocationsNear(
                params.latitude,
                params.longitude,
                params.skip,
                params.limit,
                params.search
            ).map { it }
    }

    data class Params(
        val latitude: String,
        val longitude: String,
        val skip: Int? = null,
        val limit: Int? = null,
        val search: String? = null
    )
}