package org.christophertwo.parse.feature.navigation.di

import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator
import org.christophertwo.parse.feature.navigation.navigator.HomeNavigator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NavModule
    get() = module {
        singleOf(::GlobalNavigator)
        singleOf(::HomeNavigator)
    }