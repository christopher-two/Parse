package org.christophertwo.parse.di

import org.christophertwo.parse.domain.usecase.settings.IsDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsThemeSystemUseCase
import org.christophertwo.parse.domain.usecase.settings.SetDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetThemeSystemUseCase
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