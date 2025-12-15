package org.christophertwo.parse.di

import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.IsThemeSystemUseCase
import org.christophertwo.parse.feature.settings.domain.SetDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.SetThemeSystemUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DomainModule: Module
    get() = module {
        singleOf(::IsDarkThemeUseCase)
        singleOf(::SetDarkThemeUseCase)
        singleOf(::SetDarkThemeUseCase)
        singleOf(::SetThemeSystemUseCase)
        singleOf(::IsThemeSystemUseCase)
    }