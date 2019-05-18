package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.PostLoginUseCase
import com.zigerianos.jourtrip.domain.usecases.PostRecoverPasswordByEmailUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class LoginPresenter(
    private val authManager: AuthManager,
    private val postLoginUseCase: PostLoginUseCase,
    private val postRecoverPasswordByEmailUseCase: PostRecoverPasswordByEmailUseCase
): BasePresenter<ILoginPresenter.ILoginView>(), ILoginPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateDataLogin()

        //signup(username = "Paco", email = "invitado@hotmail.com", password = "1234")
    }

    override fun loginClicked(email: String, password: String) {
        getMvpView()?.stateLoading()

        val params = PostLoginUseCase.Params(UserRequest(email = email, password = password))

        val disposable = postLoginUseCase.observable(params)
            .subscribe({ dataWithMeta ->

                val token = dataWithMeta.data
                val user = dataWithMeta.metadata

                if (token.isEmpty()) {
                    getMvpView()?.showErrorMessage(dataWithMeta.message)
                    return@subscribe
                }

                authManager.addUser(user)
                authManager.addToken(token)

                Timber.d("Patata => User: " + authManager.getUser())
                getMvpView()?.navigateToMain()

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    override fun recoveryPasswordClicked(recoveryEmail: String) {
        getMvpView()?.stateLoading()

        val params = PostRecoverPasswordByEmailUseCase.Params(UserRequest(email = recoveryEmail))

        val disposable = postRecoverPasswordByEmailUseCase.observable(params)
            .subscribe({ message ->

                /*if (message.isEmpty()) {
                    getMvpView()?.showInvalidCredentialsErrorMessage()
                    return@subscribe
                }*/

                getMvpView()?.stateDataRecoverPassword()
                getMvpView()?.showSuccessMessage(message)

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}