plugins {
    id("mocksy.android.library")
}

android {
    namespace = "com.kth.mocksy.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.inject)
}
