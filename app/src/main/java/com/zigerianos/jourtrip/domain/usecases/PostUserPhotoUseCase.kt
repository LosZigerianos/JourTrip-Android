package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.DataWithMeta
import com.zigerianos.jourtrip.data.entities.User
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import okhttp3.MultipartBody

class PostUserPhotoUseCase(
    transformer: ObservableTransformer<DataWithMeta<String, User>, DataWithMeta<String, User>>,
    private val api: ApiService
) : UseCaseWithParams<PostUserPhotoUseCase.Params, DataWithMeta<String, User>>(transformer) {

    override fun createObservable(params: Params): Observable<DataWithMeta<String, User>> {
        return api.postUserPhotoUseCase(params.userId, params.photoRequest).map { it }
    }

    data class Params(
        val userId: String,
        val photoRequest: MultipartBody.Part
    )
}