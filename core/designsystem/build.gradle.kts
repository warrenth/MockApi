plugins {
    id("mockapi.android.library")
    id("mockapi.android.library.compose")
    id("mockapi.spotless")
}

android {
    namespace = "com.kth.mockapi.core.designsystem"
}

dependencies {
    api(libs.androidx.startup)

    // image loading
    api(libs.landscapist.glide)
    api(libs.landscapist.animation)
    api(libs.landscapist.placeholder)
    api(libs.landscapist.palette)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.animation)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.constraintlayout)
    api(libs.compose.effects)
}