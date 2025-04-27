plugins {
    id("mocksy.android.library")
    id("mocksy.android.library.compose")
    id("mocksy.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("mocksy.spotless")
}

android {
    namespace = "com.kth.mocksy.core.navigation"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)
    api(libs.androidx.navigation.compose)
}
