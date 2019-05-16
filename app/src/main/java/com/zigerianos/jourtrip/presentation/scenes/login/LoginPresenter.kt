package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityUseCase
import com.zigerianos.jourtrip.domain.usecases.PostLoginUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class LoginPresenter(
    private val authManager: AuthManager,
    private val postLoginUseCase: PostLoginUseCase
): BasePresenter<ILoginPresenter.ILoginView>(), ILoginPresenter {

    override fun update() {
        super.update()

        login(email= "invitado@example.com", password = "123123")
    }

    override fun login(email: String, password: String) {
        val params = PostLoginUseCase.Params(UserRequest(email = "invitado@example.com", password = "123123"))

        val disposable = postLoginUseCase.observable(params)
            .subscribe({
                authManager.addToken(it)
                Timber.d("Patata => Token: " + it)

                Timber.d("Patata => User: " + authManager.getUser())
                //getMvpView()?.loadRooms(mRooms)
                //getMvpView()?.stateData()
            }, {
                Timber.e(it)
                //getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

}