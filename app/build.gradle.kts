plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "org.christophertwo.parse"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "org.christophertwo.parse"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Modules
    implementation(project(":core:ui"))
    implementation(project(":core:common"))

    implementation(project(":data:settings:api"))
    implementation(project(":data:settings:impl:datastore"))

    implementation(project(":domain:models"))
    implementation(project(":domain:usecase"))

    implementation(project(":feature:navigation"))

    implementation(project(":feature:home:presentation"))

    implementation(project(":feature:books:presentation"))
    implementation(project(":feature:books:domain"))

    implementation(project(":feature:settings:presentation"))
    implementation(project(":feature:settings:domain"))

    implementation(project(":feature:book:presentation"))

    // Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3.android)

    // Utils
    implementation(libs.pdfbox.android)

    //Nav 3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)

    // UI & Design System
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.material.kolor)
    implementation(libs.material.icons.ext)
    implementation(libs.core.splashscreen)
    implementation(libs.qrose)
    implementation(libs.accompanist.permissions)

    // Dependency Injection
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.navigation3)
    implementation(libs.koin.compose.viewmodel)

    // Data, Network & Auth
    implementation(libs.datastore.pref)
    implementation(libs.datastore.pref.core)

    // Core & Kotlin X
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization)

    // Test & Tools
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}