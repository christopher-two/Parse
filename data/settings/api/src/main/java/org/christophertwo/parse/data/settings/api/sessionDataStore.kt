package org.christophertwo.parse.data.settings.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")