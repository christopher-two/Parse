package org.christophertwo.parse.data.settings.api.di

import org.koin.core.qualifier.named

object DiQualifiers {
    val SESSION_DATASTORE = named("SessionDataStore")
    val SETTINGS_DATASTORE = named("SettingsDataStore")
}