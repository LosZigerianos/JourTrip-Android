package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Place
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetPlacesByCityUseCase(
    transformer: ObservableTransformer<List<Place>, List<Place>>,
    private val api: ApiService
) : UseCaseWithParams<GetPlacesByCityUseCase.Params, List<Place>>(transformer) {

    override fun createObservable(params: Params): Observable<List<Place>> {
        return api.getPlacesByCity(params.city).map { it.data }
    }

    data class Params(val city: String)
}
