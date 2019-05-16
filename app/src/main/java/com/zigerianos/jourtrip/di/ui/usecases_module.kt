package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.domain.base.transformers.AsyncObservableTransformer
import com.zigerianos.jourtrip.domain.usecases.GetPlacesByCityUseCase
import org.koin.dsl.module.module

val useCasesModule = module {

    factory {
        GetPlacesByCityUseCase(
            AsyncObservableTransformer(),
            get()
        )
    }

}
