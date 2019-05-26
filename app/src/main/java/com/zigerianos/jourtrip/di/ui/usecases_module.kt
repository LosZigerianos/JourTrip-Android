package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.domain.base.transformers.AsyncObservableTransformer
import com.zigerianos.jourtrip.domain.usecases.*
import org.koin.dsl.module.module

val useCasesModule = module {

    factory {
        PostLoginUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PostSignupUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PostRecoverPasswordByEmailUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetLocationsByCityUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetLocationsByCityAndPlaceUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetUserMeUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PutUserDataUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PutPasswordUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PostUserPhotoUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

}
