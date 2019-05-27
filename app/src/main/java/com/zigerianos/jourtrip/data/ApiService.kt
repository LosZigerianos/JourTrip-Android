package com.zigerianos.jourtrip.data

import com.zigerianos.jourtrip.data.entities.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    // LOGIN
    @POST("users/login")
    fun postLoginUseCase(
        @Body request: UserRequest
    ): Observable<DataWithMeta<String, User>>

    @POST("users/signup")
    fun postSingupUseCase(
        @Body request: UserRequest
    ): Observable<Data<User>>

    @POST("users/recoverPassword")
    fun postRecoverPasswordUseCase(
        @Body request: UserRequest
    ): Observable<Data<String>>

    // LOCATIONS
    @GET("locations/city/{city}")
    fun getLocationsByCity(
        @Path("city") city: String
    ): Observable<Data<List<Location>>>

    @GET("locations/{city}/place/{place}")
    fun getLocationsByCityAndPlace(
        @Path("city") city: String,
        @Path("place") place: String
    ): Observable<Data<List<Location>>>

    @GET("locations/near")
    fun getLocationsNear(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Observable<Data<List<Location>>>

    // USER
    @GET("users/userId/{userId}")
    fun getUserMeUseCase(
        @Path("userId") userId: String
    ): Observable<Data<User>>

    @PUT("users/userId/{userId}/update")
    fun putUserDataUseCase(
        @Path("userId") userId: String,
        @Body request: UserRequest
    ): Observable<Data<User>>

    @PUT("users/userId/{userId}/change-password")
    fun putPasswordUseCase(
        @Path("userId") userId: String,
        @Body request: PasswordRequest
    ): Observable<Data<User>>

    @Multipart
    @PUT("users/userId/{userId}/photo")
    fun postUserPhotoUseCase(
        @Path("userId") userId: String,
        @Part image: MultipartBody.Part
    ): Observable<DataWithMeta<String, User>>

    @GET("users/profile/{userId}")
    fun getUserProfileUseCase(
        @Path("userId") userId: String
    ): Observable<Data<UserProfile>>
}