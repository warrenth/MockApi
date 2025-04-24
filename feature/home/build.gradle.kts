plugins {
    id("mockapi.android.library")
    id("mockapi.android.library.compose")
    id("mockapi.android.feature")
    id("mockapi.android.hilt")
    id("mockapi.spotless")
    alias(libs.plugins.screenshot)
}

android {
    namespace = "com.kth.mockapi.feature.home"

    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.runtime)

    screenshotTestImplementation(libs.androidx.ui.tooling)
}
