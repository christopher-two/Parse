package org.christophertwo.parse.feature.auth.presentation

sealed interface AuthAction {
    object Login : AuthAction
}