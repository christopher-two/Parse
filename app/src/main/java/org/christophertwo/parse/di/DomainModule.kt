package org.christophertwo.parse.di

import org.christophertwo.parse.domain.usecase.settings.GetContrastUseCase
import org.christophertwo.parse.domain.usecase.settings.GetFontSizeUseCase
import org.christophertwo.parse.domain.usecase.settings.GetThemeColorUseCase
import org.christophertwo.parse.domain.usecase.settings.IsAmoledThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsThemeSystemUseCase
import org.christophertwo.parse.domain.usecase.settings.SetAmoledThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetContrastUseCase
import org.christophertwo.parse.domain.usecase.settings.SetDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetFontSizeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetThemeColorUseCase
import org.christophertwo.parse.domain.usecase.settings.SetThemeSystemUseCase
import org.christophertwo.parse.feature.home.domain.ImportPdfUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DomainModule: Module
    get() = module {
        // Theme / appearance
        singleOf(::IsDarkThemeUseCase)
        singleOf(::SetDarkThemeUseCase)
        singleOf(::IsThemeSystemUseCase)
        singleOf(::SetThemeSystemUseCase)

        singleOf(::GetFontSizeUseCase)
        singleOf(::SetFontSizeUseCase)

        singleOf(::GetThemeColorUseCase)
        singleOf(::SetThemeColorUseCase)

        singleOf(::IsAmoledThemeUseCase)
        singleOf(::SetAmoledThemeUseCase)

        singleOf(::GetContrastUseCase)
        singleOf(::SetContrastUseCase)

        // Home
        singleOf(::ImportPdfUseCase)
    }