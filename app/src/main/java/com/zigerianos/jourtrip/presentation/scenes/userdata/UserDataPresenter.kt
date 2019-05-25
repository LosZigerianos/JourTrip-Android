package com.zigerianos.jourtrip.presentation.scenes.userdata

import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.PasswordRequest
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.GetUserMeUseCase
import com.zigerianos.jourtrip.domain.usecases.PutPasswordUseCase
import com.zigerianos.jourtrip.domain.usecases.PutUserDataUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class UserDataPresenter(
    private val authManager: AuthManager,
    private val getUserMeUseCase: GetUserMeUseCase,
    private val putUserDataUseCase: PutUserDataUseCase,
    private val putPasswordUseCase: PutPasswordUseCase
): BasePresenter<IUserDataPresenter.IUserDataView>(), IUserDataPresenter {

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()

        val disposable = getUserMeUseCase.observable()
            .subscribe({ user ->
                Timber.d("Patata => User: $user ")
                getMvpView()?.loadUser(user)
                getMvpView()?.stateData()
                authManager.updateUser(user)
            }, {
                Timber.e(it)
                getMvpView()?.stateData()
            })

        addDisposable(disposable)
    }

    override fun updateDataClicked(fullname: String, username: String, email: String) {
        val userRequest = UserRequest(
            fullname = if (fullname.isNotEmpty()) fullname else null,
            username = if (username.isNotEmpty()) username else null,
            email = email
        )

        requestUpdateUserData(userRequest)
    }

    override fun updatePasswordClicked(oldPassword: String, newPassword: String) {
        val passwordRequest = PasswordRequest(
            password = oldPassword,
            newPassword = newPassword,
            passwordConfirmation = newPassword
        )

        requestUpdatePassword(passwordRequest)
    }

    override fun logoutClicked() {
        authManager.deleteUser()
        getMvpView()?.navigateToInit()
    }

    private fun requestUpdateUserData(userRequest: UserRequest) {
        getMvpView()?.stateLoading()

        val params = PutUserDataUseCase.Params(userRequest)

        val disposable = putUserDataUseCase.observable(params)
            .subscribe({ success ->

                if (!success) {
                    getMvpView()?.showErrorMessage()
                }

                getMvpView()?.stateData()
                getMvpView()?.showSuccessMessage()

            }, {
                Timber.e(it)
                getMvpView()?.showErrorMessage()
            })

        addDisposable(disposable)
    }

    private fun requestUpdatePassword(passwordRequest: PasswordRequest) {
        getMvpView()?.stateLoading()

        val params = PutPasswordUseCase.Params(passwordRequest)

        val disposable = putPasswordUseCase.observable(params)
            .subscribe({ success ->

                if (!success) {
                    getMvpView()?.showErrorMessage()
                }

                getMvpView()?.stateData()
                getMvpView()?.showSuccessMessage()

            }, {
                Timber.e(it)
                getMvpView()?.showErrorMessage()
            })

        addDisposable(disposable)
    }
}