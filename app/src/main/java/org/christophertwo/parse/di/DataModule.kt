package org.christophertwo.parse.di

import org.christophertwo.parse.data.settings.api.SettingsRepository
import org.christophertwo.parse.data.settings.api.di.DiQualifiers
import org.christophertwo.parse.data.settings.api.sessionDataStore
import org.christophertwo.parse.data.settings.api.settingsDataStore
import org.christophertwo.parse.data.settings.impl.datastore.SettingsRepositoryImpl
import org.christophertwo.parse.feature.home.data.HomeRepositoryImpl
import org.christophertwo.parse.feature.home.domain.HomeRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val DataModule: Module
    get() = module {
        single(qualifier = DiQualifiers.SESSION_DATASTORE) {
            androidContext().sessionDataStore
        }
        single(qualifier = DiQualifiers.SETTINGS_DATASTORE) {
            androidContext().settingsDataStore
        }

        single<SettingsRepository> {
            SettingsRepositoryImpl(get(qualifier = DiQualifiers.SETTINGS_DATASTORE))
        }

        // Home
        single<HomeRepository> { HomeRepositoryImpl(appContext = androidContext()) }
    }