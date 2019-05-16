package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.Location
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityUseCase
import com.zigerianos.jourtrip.domain.usecases.PostLoginUseCase
import com.zigerianos.jourtrip.domain.usecases.PostSignupUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class LoginPresenter(
    private val authManager: AuthManager,
    private val postLoginUseCase: PostLoginUseCase,
    private val postSignupUseCase: PostSignupUseCase
): BasePresenter<ILoginPresenter.ILoginView>(), ILoginPresenter {

    override fun update() {
        super.update()

        login(email= "invitado@example.com", password = "123123")

        //signup(username = "Paco", email = "invitado@hotmail.com", password = "1234")
    }

    override fun login(email: String, password: String) {
        val params = PostLoginUseCase.Params(UserRequest(email = email, password = password))

        val disposable = postLoginUseCase.observable(params)
            .subscribe({ token ->
                authManager.addToken(token)
                Timber.d("Patata => Token: " + token)

                Timber.d("Patata => User: " + authManager.getUser())
                //getMvpView()?.loadRooms(mRooms)
                //getMvpView()?.stateData()
            }, {
                Timber.e(it)
                //getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

    private fun signup(username: String, email: String, password: String) {
        val params = PostSignupUseCase.Params(UserRequest(username = username, email = email, password = password))

        val disposable = postSignupUseCase.observable(params)
            .subscribe({ user ->
                authManager.addUser(user)
                Timber.d("Patata => UserReceived: " + user)

                Timber.d("Patata => UserStored: " + authManager.getUser())
                //getMvpView()?.loadRooms(mRooms)
                //getMvpView()?.stateData()
            }, {
                Timber.e(it)
                //getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }
}