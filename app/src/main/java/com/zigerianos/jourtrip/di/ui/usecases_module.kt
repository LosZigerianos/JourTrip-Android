package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.domain.base.transformers.AsyncObservableTransformer
import com.zigerianos.jourtrip.domain.usecases.*
import org.koin.dsl.module.module

val useCasesModule = module {

    factory {
        GetTokenValidationUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

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
        GetLocationByNameUseCase(
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

    factory {
        GetUserProfileUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetLocationsNearUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetTimeLineUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetCommentsByUserUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetCommentsByLocationUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PostAddCommentToLocationUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetFollowersByUserUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        GetFollowingByUserUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        PostAddFollowingUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

    factory {
        DeleteFollowingUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }
}
