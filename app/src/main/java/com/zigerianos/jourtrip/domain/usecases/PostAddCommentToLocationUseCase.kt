package com.zigerianos.jourtrip.domain.usecases

import com.zigerianos.jourtrip.data.ApiService
import com.zigerianos.jourtrip.data.entities.Comment
import com.zigerianos.jourtrip.data.entities.CommentRequest
import com.zigerianos.jourtrip.data.entities.Data
import com.zigerianos.jourtrip.domain.base.usecases.UseCaseWithParams
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class PostAddCommentToLocationUseCase(
    transformer: ObservableTransformer<Data<Comment>, Data<Comment>>,
    private val api: ApiService
) : UseCaseWithParams<PostAddCommentToLocationUseCase.Params, Data<Comment>>(transformer) {

    override fun createObservable(params: Params): Observable<Data<Comment>> {
        return api.postAddCommentToLocationUseCase(params.commentRequest).map { it }
    }

    data class Params(val commentRequest: CommentRequest)
}
