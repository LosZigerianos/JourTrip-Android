package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.CommentResponse
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DeleteCommentUseCase(
    transformer: ObservableTransformer<Data<CommentResponse>, Data<CommentResponse>>,
    private val api: ApiService
) : UseCaseWithParams<DeleteCommentUseCase.Params, Data<CommentResponse>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<CommentResponse>> {
        return api.deleteCommentUseCase(params.commentId)
            .map { Data(data = it.data, success = it.success) }
    }

    data class Params(
        val commentId: String
    )
}