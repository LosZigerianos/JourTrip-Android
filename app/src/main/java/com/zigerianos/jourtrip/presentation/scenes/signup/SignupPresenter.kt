package com.zigerianos.jourtrip.presentation.scenes.signup

import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.PostSignupUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber



class SignupPresenter(
    private val postSignupUseCase: PostSignupUseCase
): BasePresenter<ISignupPresenter.ISignupView>(), ISignupPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()
    }

    override fun signupClicked(fullname: String, username: String, email: String, password: String) {
        getMvpView()?.stateLoading()

        val params = PostSignupUseCase.Params(UserRequest(username = if (username.isNotEmpty()) username else null, email = email, password = password))

        val disposable = postSignupUseCase.observable(params)
            .subscribe({ response ->

                if (!response.error.isNullOrEmpty()) {
                    getMvpView()?.showMessage(response.error)
                    getMvpView()?.stateData()
                    return@subscribe
                }

                getMvpView()?.navigateToBack()
                getMvpView()?.showSuccessMessage()
            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

}