package org.christophertwo.parse.data.settings.api

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    val THEME_SYSTEM = booleanPreferencesKey("theme_system")
}
