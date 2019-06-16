package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DeleteCommentUseCase(
    transformer: ObservableTransformer<Data<Comment>, Data<Comment>>,
    private val api: ApiService
) : UseCaseWithParams<DeleteCommentUseCase.Params, Data<Comment>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<Comment>> {
        return api.deleteCommentUseCase(params.commentId)
            .map { Data(data = it.data, success = it.success) }
    }

    data class Params(
        val commentId: String
    )
}