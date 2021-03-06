package com.zigerianos.jourtrip.data

import com.zigerianos.jourtrip.data.entities.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.http.HTTP



interface ApiService {

    // LOGIN
    @GET("token/validate")
    fun getTokenValidationUseCase(
        @Query("token") token: String?
    ): Observable<TokenResponse>

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
        @Path("city") city: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Location>>>

    @GET("locations/place/{place}")
    fun getLocationsByName(
        @Path("place") place: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Location>>>

    @GET("locations/{city}/place/{place}")
    fun getLocationsByCityAndPlace(
        @Path("city") city: String,
        @Path("place") place: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Location>>>

    @GET("locations/near")
    fun getLocationsNear(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?,
        @Query("search") search: String?
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
        @Path("userId") userId: String,
        @Query("skipComments") skip: Int?,
        @Query("limitComments") limit: Int?
    ): Observable<Data<UserProfile>>

    @GET("users/userId/{userId}/followers")
    fun getFollowersByUserUseCase(
        @Path("userId") userId: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<User>>>

    @GET("users/userId/{userId}/following")
    fun getFollowingByUserUseCase(
        @Path("userId") userId: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<User>>>

    @POST("users/userId/{userId}/following/add")
    fun postAddFollowingUseCase(
        @Path("userId") userId: String,
        @Body request: FollowingRequest
    ): Observable<DataWithMeta<User, User>>

    @HTTP(method = "DELETE", path = "users/userId/{userId}/following/delete", hasBody = true)
    fun deleteFollowingUseCase(
        @Path("userId") userId: String,
        @Body request: FollowingRequest
    ): Observable<DataWithMeta<User, User>>

    @GET("users/search")
    fun getContactsByNameUseCase(
        @Query("query") name: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<User>>>

    // Comments
    @GET("comments/userId/{userId}/timeline")
    fun getTimeLineUseCase(
        @Path("userId") userId: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Comment>>>

    // Comments
    @GET("comments/user/{userId}")
    fun getCommentsByUserUseCase(
        @Path("userId") userId: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Comment>>>

    @GET("comments/location/{locationId}")
    fun getCommentsByLocationUseCase(
        @Path("locationId") userId: String,
        @Query("skip") skip: Int?,
        @Query("limit") limit: Int?
    ): Observable<Data<List<Comment>>>

    @POST("comments/add")
    fun postAddCommentToLocationUseCase(
        @Body request: CommentRequest
    ): Observable<Data<Comment>>

    @DELETE("comments/{commentId}/delete")
    fun deleteCommentUseCase(
        @Path("commentId") commentId: String
    ): Observable<Data<Comment>>


}