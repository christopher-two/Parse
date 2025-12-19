package org.christophertwo.parse.di

import org.christophertwo.parse.core.common.route.RouteGlobal
import org.christophertwo.parse.core.common.route.RouteHome
import org.christophertwo.parse.feature.auth.presentation.AuthRoot
import org.christophertwo.parse.feature.auth.presentation.AuthViewModel
import org.christophertwo.parse.feature.book.presentation.BookRoot
import org.christophertwo.parse.feature.book.presentation.BookViewModel
import org.christophertwo.parse.feature.books.presentation.BooksRoot
import org.christophertwo.parse.feature.books.presentation.BooksViewModel
import org.christophertwo.parse.feature.home.presentation.HomeRoot
import org.christophertwo.parse.feature.home.presentation.HomeViewModel
import org.christophertwo.parse.feature.settings.presentation.SettingsRoot
import org.christophertwo.parse.feature.settings.presentation.SettingsViewModel
import org.christophertwo.parse.main.StartupViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val ScreenModules: Module
    get() = module {
        // Global
        navigation<RouteGlobal.Home> { HomeRoot(viewModel = koinViewModel()) }
        navigation<RouteGlobal.Settings> { SettingsRoot(viewModel = koinViewModel()) }
        navigation<RouteGlobal.Book> { route ->
            BookRoot(viewModel = koinViewModel { parametersOf(route.id) })
        }
        navigation<RouteGlobal.Auth> { AuthRoot(viewModel = koinViewModel()) }

        // Home
        navigation<RouteHome.Books> { BooksRoot(viewModel = koinViewModel()) }

        // ViewModel
        viewModelOf(::HomeViewModel)
        viewModelOf(::BooksViewModel)
        viewModelOf(::SettingsViewModel)
        viewModelOf(::AuthViewModel)
        viewModel { params -> BookViewModel(id = params.get()) }

        viewModelOf(::StartupViewModel)
    }