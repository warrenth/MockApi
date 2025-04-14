plugins {
    id("mockapi.android.application")
    id("mockapi.android.application.compose")
    id("mockapi.android.hilt")
    id("mockapi.spotless")
    id("kotlin-parcelize")
    alias(libs.plugins.google.secrets)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.kth.mockapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kth.mockapi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
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
        buildConfig = true
    }
}

dependencies {
    // Core
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)

    // Features
    implementation(projects.feature.home)
    implementation(projects.feature.article)


    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsExtended)

    // Compose Image Loading
    implementation(libs.landscapist.glide)
    implementation(libs.landscapist.animation)
    implementation(libs.landscapist.placeholder)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Network
    implementation(libs.sandwich)
    implementation(libs.okhttp.logging)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.profileinstaller)
  //  baselineProfile((project(":baselineprofile")))
}