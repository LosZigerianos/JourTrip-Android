package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetLocationsByCityAndPlaceUseCase(
    transformer: ObservableTransformer<List<Location>, List<Location>>,
    private val api: ApiService
) : UseCaseWithParams<GetLocationsByCityAndPlaceUseCase.Params, List<Location>>(transformer) {

    override fun createObservable(params: Params): Observable<List<Location>> {
        return api.getLocationsByCityAndPlace(params.city, params.place).map { it.data }
    }

    data class Params(
        val city: String,
        val place: String
    )
}