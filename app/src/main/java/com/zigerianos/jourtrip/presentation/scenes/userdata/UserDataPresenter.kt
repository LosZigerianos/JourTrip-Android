package com.zigerianos.jourtrip.presentation.scenes.userdata

import android.content.Context
import android.graphics.Bitmap
import com.zigerianos.jourtrip.auth.AuthManager
import com.zigerianos.jourtrip.data.entities.PasswordRequest
import com.zigerianos.jourtrip.data.entities.UserRequest
import com.zigerianos.jourtrip.domain.usecases.GetUserMeUseCase
import com.zigerianos.jourtrip.domain.usecases.PostUserPhotoUseCase
import com.zigerianos.jourtrip.domain.usecases.PutPasswordUseCase
import com.zigerianos.jourtrip.domain.usecases.PutUserDataUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import com.zigerianos.jourtrip.utils.ConvertFileFromBitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File

class UserDataPresenter(
    private val authManager: AuthManager,
    private val getUserMeUseCase: GetUserMeUseCase,
    private val putUserDataUseCase: PutUserDataUseCase,
    private val putPasswordUseCase: PutPasswordUseCase,
    private val postUserPhotoUseCase: PostUserPhotoUseCase
) : BasePresenter<IUserDataPresenter.IUserDataView>(), IUserDataPresenter {

    private val userId: String = authManager.getUserId() ?: ""

    override fun update() {
        super.update()

        getMvpView()?.setupToolbar()
        getMvpView()?.setupViews()

        requestUserData()
    }

    override fun reloadDataClicked() {
        getMvpView()?.stateLoading()

        requestUserData()
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
            oldPassword = oldPassword,
            newPassword = newPassword
        )

        requestUpdatePassword(passwordRequest)
    }

    override fun logoutClicked() {
        authManager.deleteUser()
        getMvpView()?.navigateToInit()
    }

    override fun uploadImage(context: Context, bitmap: Bitmap) {
        ConvertFileFromBitmap(context, bitmap) { file ->
            requestUpdatePhoto(file)
        }.execute()
    }

    private fun requestUserData() {
        val params = GetUserMeUseCase.Params(userId)

        val disposable = getUserMeUseCase.observable(params)
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

    private fun requestUpdateUserData(userRequest: UserRequest) {
        getMvpView()?.stateLoading()

        val params = PutUserDataUseCase.Params(userId, userRequest)

        val disposable = putUserDataUseCase.observable(params)
            .subscribe({ success ->

                if (!success) {
                    getMvpView()?.showErrorMessage()
                    getMvpView()?.stateData()
                    return@subscribe
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

        val params = PutPasswordUseCase.Params(userId, passwordRequest)

        val disposable = putPasswordUseCase.observable(params)
            .subscribe({ success ->

                if (!success) {
                    getMvpView()?.showErrorMessage()
                    getMvpView()?.stateData()
                    return@subscribe
                }

                getMvpView()?.stateData()
                getMvpView()?.showSuccessMessage()

            }, {
                Timber.e(it)
                getMvpView()?.showErrorMessage()
            })

        addDisposable(disposable)
    }

    private fun requestUpdatePhoto(file: File) {
        val params = PostUserPhotoUseCase.Params(
            userId,
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                RequestBody.create(MediaType.parse("image/*"), file)
            )
        )

        val disposable = postUserPhotoUseCase.observable(params)
            .subscribe({ response ->

                if (!response.success) {
                    getMvpView()?.showErrorMessage()
                    getMvpView()?.stateData()
                    return@subscribe
                }

                getMvpView()?.loadUser(response.metadata)

                getMvpView()?.stateData()
                getMvpView()?.showSuccessMessage()

            }, {
                Timber.e(it)
                getMvpView()?.stateData()
            })

        addDisposable(disposable)
    }
}