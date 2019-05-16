package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.domain.base.transformers.AsyncObservableTransformer
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityAndPlaceUseCase
import com.zigerianos.jourtrip.domain.usecases.GetLocationsByCityUseCase
import com.zigerianos.jourtrip.domain.usecases.PostLoginUseCase
import org.koin.dsl.module.module

val useCasesModule = module {

    factory {
        PostLoginUseCase(
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

}
