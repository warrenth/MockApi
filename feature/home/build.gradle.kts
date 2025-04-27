plugins {
    id("mocksy.android.library")
    id("mocksy.android.library.compose")
    id("mocksy.android.feature")
    id("mocksy.android.hilt")
    id("mocksy.spotless")
    alias(libs.plugins.screenshot)
}

android {
    namespace = "com.kth.mocksy.feature.home"

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
