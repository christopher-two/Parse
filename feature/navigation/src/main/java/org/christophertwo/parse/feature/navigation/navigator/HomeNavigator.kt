package org.christophertwo.parse.feature.navigation.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavKey
import org.christophertwo.parse.core.common.RouteHome
import org.christophertwo.parse.feature.navigation.utils.AppTab

class HomeNavigator {
    var currentTab by mutableStateOf(AppTab.Books)
        private set

    private val stacks = mapOf(
        AppTab.Books to mutableStateListOf<NavKey>(RouteHome.Books)
    )

    val currentStack: List<NavKey>
        get() = stacks[currentTab] ?: emptyList()


    fun switchTab(tab: AppTab) {
        currentTab = tab
    }

    fun navigateTo(route: NavKey) {
        stacks[currentTab]?.add(route)
    }

    fun back(): Boolean {
        val activeStack = stacks[currentTab] ?: return false

        if (activeStack.size > 1) {
            activeStack.removeAt(activeStack.lastIndex)
            return true
        }

        if (currentTab != AppTab.Books) {
            currentTab = AppTab.Books
            return true
        }

        return false
    }
}