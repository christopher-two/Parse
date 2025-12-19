package org.christophertwo.parse.feature.auth.presentation

data class AuthState(
    val isError: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)