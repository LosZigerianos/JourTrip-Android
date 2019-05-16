package com.zigerianos.jourtrip.data

import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.Token
import com.zigerianos.jourtrip.data.entities.UserRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users/login")
    fun postLoginUseCase(
        @Body request: UserRequest
    ): Observable<Token<String>>

    @GET("locations/{city}")
    fun getLocationsByCity(
        @Path("city") city: String
    ): Observable<Data<List<Location>>>

    @GET("locations/{city}/{place}")
    fun getLocationsByCityAndPlace(
        @Path("city") city: String,
        @Path("place") place: String
    ): Observable<Data<List<Location>>>

}