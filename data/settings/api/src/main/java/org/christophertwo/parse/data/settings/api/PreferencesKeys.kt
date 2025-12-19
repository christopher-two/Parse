package org.christophertwo.parse.data.settings.api

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    val THEME_SYSTEM = booleanPreferencesKey("theme_system")
    val FONT_SIZE = floatPreferencesKey("font_size")
    val THEME_COLOR = intPreferencesKey("theme_color")
    val AMOLED_THEME = booleanPreferencesKey("amoled_theme")
    val CONTRAST = stringPreferencesKey("contrast")
}
