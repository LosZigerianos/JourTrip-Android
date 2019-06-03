package com.zigerianos.jourtrip.di.ui

import com.zigerianos.jourtrip.presentation.scenes.contacts.ContactsPresenter
import com.zigerianos.jourtrip.presentation.scenes.contacts.IContactsPresenter
import com.zigerianos.jourtrip.utils.ContactAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val fragmentContactsModule = module {
    factory { ContactAdapter(androidContext(), get()) }

    factory<IContactsPresenter> {
        ContactsPresenter(
            get(),
            get(),
            get()
        )
    }
}