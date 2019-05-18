package com.zigerianos.jourtrip.presentation.scenes.signup

import com.zigerianos.jourtrip.domain.usecases.PostSignupUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter

class SignupPresenter(
    private val postSignupUseCase: PostSignupUseCase
): BasePresenter<ISignupPresenter.ISignupView>(), ISignupPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()
        getMvpView()?.stateData()

        //signup(username = "Paco", email = "invitado@hotmail.com", password = "1234")
    }



    /*private fun signup(username: String, email: String, password: String) {
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
    }*/

}