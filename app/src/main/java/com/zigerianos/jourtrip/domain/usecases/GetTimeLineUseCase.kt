package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.domain.base.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class GetTimeLineUseCase(
    transformer: ObservableTransformer<List<Location>, List<Location>>,
    private val api: ApiService
)  : UseCase<List<Location>>(transformer) {

    override fun createObservable(): Observable<List<Location>> {
        return api.getTimeLineUseCase().map { it.data }
    }
}