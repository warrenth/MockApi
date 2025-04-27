plugins {
    id("mocksy.android.library")
    id("mocksy.android.library.compose")
    id("mocksy.android.feature")
    id("mocksy.android.hilt")
    id("mocksy.spotless")
}

android {
    namespace = "com.kth.mocksy.feature.article"
}

dependencies {
    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.runtime)
}
