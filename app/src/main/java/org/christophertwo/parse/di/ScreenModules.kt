package org.christophertwo.parse.di

import org.christophertwo.parse.StartupViewModel
import org.christophertwo.parse.core.common.route.RouteGlobal
import org.christophertwo.parse.core.common.route.RouteHome
import org.christophertwo.parse.feature.books.presentation.BooksRoot
import org.christophertwo.parse.feature.books.presentation.BooksViewModel
import org.christophertwo.parse.feature.home.presentation.HomeRoot
import org.christophertwo.parse.feature.home.presentation.HomeViewModel
import org.christophertwo.parse.feature.settings.presentation.SettingsRoot
import org.christophertwo.parse.feature.settings.presentation.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val ScreenModules: Module
    get() = module {
        // Global
        navigation<RouteGlobal.Home> { HomeRoot(viewModel = koinViewModel()) }
        navigation<RouteGlobal.Settings> { SettingsRoot(viewModel = koinViewModel()) }

        // Home
        navigation<RouteHome.Books> { BooksRoot(viewModel = koinViewModel()) }

        // ViewModel
        viewModelOf(::HomeViewModel)
        viewModelOf(::BooksViewModel)
        viewModelOf(::SettingsViewModel)

        viewModelOf(::StartupViewModel)
    }