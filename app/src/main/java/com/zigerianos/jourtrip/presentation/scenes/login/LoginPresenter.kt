package com.zigerianos.jourtrip.presentation.scenes.login

import android.content.Context
import com.google.gson.Gson
import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.ErrorResponse
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.ServiceError
import com.zigerianos.jourtrip.domain.usecases.PostLoginUseCase
import com.zigerianos.jourtrip.domain.usecases.PostRecoverPasswordByEmailUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import retrofit2.HttpException
import timber.log.Timber

class LoginPresenter(
    private val authManager: AuthManager,
    private val postLoginUseCase: PostLoginUseCase,
    private val postRecoverPasswordByEmailUseCase: PostRecoverPasswordByEmailUseCase,
    private val gson: Gson
) : BasePresenter<ILoginPresenter.ILoginView>(), ILoginPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateDataLogin()
    }

    override fun loginClicked(email: String, password: String, context: Context) {
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
                getMvpView()?.navigateToHome()
            }, {
                Timber.e(it)

                val error = it as? HttpException
                error?.response()?.errorBody()?.let { errorResponse ->
                    val serviceError = gson.fromJson(errorResponse.string().toString(), ErrorResponse::class.java)

                    when (ServiceError.getServiceError(serviceError.error)) {
                        ServiceError.UNAUTHORIZED, ServiceError.CREDENTIALS_INVALID -> {
                            getMvpView()?.showCredentialsErrorMessage()
                            return@subscribe
                        }
                        ServiceError.TOKEN_EXPIRED, ServiceError.NOT_FOUND, ServiceError.UNKNOWN -> {
                            getMvpView()?.stateError()
                            return@subscribe
                        }
                    }
                } ?: run {
                    getMvpView()?.stateError()
                    return@subscribe
                }

            })

        addDisposable(disposable)
    }

    override fun recoveryPasswordClicked(recoveryEmail: String) {
        getMvpView()?.stateLoading()

        val params = PostRecoverPasswordByEmailUseCase.Params(UserRequest(email = recoveryEmail))

        val disposable = postRecoverPasswordByEmailUseCase.observable(params)
            .subscribe({ message ->

                getMvpView()?.stateDataRecoverPassword()
                getMvpView()?.showSuccessMessage(message)

            }, {
                Timber.e(it)
                getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    override fun hasBiometricPermission(value: Boolean) {
        authManager.addBiometricPermission(value)
    }
}