package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetLocationByNameUseCase(
    transformer: ObservableTransformer<Data<List<Location>>, Data<List<Location>>>,
    private val api: ApiService
) : UseCaseWithParams<GetLocationByNameUseCase.Params, Data<List<Location>>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<List<Location>>> {
        return api
            .getLocationsByName(
                params.place,
                params.skip,
                params.limit
            ).map { it }
    }

    data class Params(
        val place: String,
        val skip: Int? = null,
        val limit: Int? = null
    )
}