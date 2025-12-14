package org.christophertwo.parse.feature.navigation.navigator

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import org.christophertwo.parse.core.common.RouteGlobal

class GlobalNavigator {
    private val _rootBackStack = mutableStateListOf<NavKey>(RouteGlobal.Home)
    val rootBackStack: List<NavKey> get() = _rootBackStack

    fun back() {
        if(rootBackStack.isEmpty()) return
        _rootBackStack.removeLastOrNull()
    }

    fun navigateTo(route: RouteGlobal) {
        _rootBackStack.add(route)
    }

    fun navigateToSettings() {
        navigateTo(RouteGlobal.Settings)
    }

    fun backTo(targetScreen: NavKey) {
        if (_rootBackStack.isEmpty()) return
        if (_rootBackStack.last() == targetScreen) return

        while(_rootBackStack.isNotEmpty() && _rootBackStack.last() != targetScreen){
            _rootBackStack.removeLastOrNull()
        }
    }
}