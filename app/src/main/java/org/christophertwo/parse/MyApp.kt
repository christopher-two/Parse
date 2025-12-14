package org.christophertwo.parse

import android.app.Application
import org.christophertwo.parse.di.DataModule
import org.christophertwo.parse.di.DomainModule
import org.christophertwo.parse.di.ScreenModules
import org.christophertwo.parse.feature.navigation.di.NavModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    NavModule,
                    ScreenModules,
                    DomainModule,
                    DataModule
                )
            )
        }
    }
}