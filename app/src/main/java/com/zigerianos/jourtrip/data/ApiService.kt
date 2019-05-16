package com.zigerianos.jourtrip.data

import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.data.entities.Place
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("locations/{city}")
    fun getPlacesByCity(
        @Path("city") city: String
    ): Observable<Data<List<Place>>>

}