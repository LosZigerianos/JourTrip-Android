package com.zigerianos.jourtrip.presentation.scenes.login

import com.zigerianos.jourtrip.data.entities.Place
import com.zigerianos.jourtrip.domain.usecases.GetPlacesByCityUseCase
import com.zigerianos.jourtrip.presentation.base.BasePresenter
import timber.log.Timber

class LoginPresenter(
    private val getPlacesByCityUseCase: GetPlacesByCityUseCase
): BasePresenter<ILoginPresenter.ILoginView>(), ILoginPresenter {

    private lateinit var mPlaces: List<Place>

    override fun update() {
        super.update()

        val params = GetPlacesByCityUseCase.Params(city = "zaragoza")

        val disposable = getPlacesByCityUseCase.observable(params)
            .subscribe({
                mPlaces = it
                if (mPlaces.isEmpty()) {
                    //getMvpView()?.stateEmpty()
                    return@subscribe
                }
                Timber.d("Patata" + mPlaces)

                //getMvpView()?.loadRooms(mRooms)
                //getMvpView()?.stateData()
            }, {
                Timber.e(it)
                //getMvpView()?.stateError()
            })

        addDisposable(disposable)
    }

}