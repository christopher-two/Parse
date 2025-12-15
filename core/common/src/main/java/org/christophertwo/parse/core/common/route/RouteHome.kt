package org.christophertwo.parse.core.common.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class RouteHome : NavKey {
    @Serializable
    data object Books : RouteHome()
}