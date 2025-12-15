package org.christophertwo.parse.core.common.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class RouteGlobal: NavKey {
    @Serializable
    data object Home : RouteGlobal()

    @Serializable
    data object Settings : RouteGlobal()

    @Serializable
    data class Book(val id: String) : RouteHome()

    @Serializable
    data object Error : RouteHome()
}