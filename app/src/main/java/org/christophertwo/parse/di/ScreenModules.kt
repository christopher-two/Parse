package org.christophertwo.parse.di

import org.christophertwo.parse.feature.home.presentation.HomeRoot
import org.christophertwo.parse.feature.home.presentation.HomeViewModel
import org.christophertwo.parse.core.common.RouteGlobal
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val ScreenModules: Module
    get() = module {
        navigation<RouteGlobal.Home> { HomeRoot(viewModel = koinViewModel()) }

        viewModelOf(::HomeViewModel)
    }