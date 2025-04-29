plugins {
    id("mocksy.android.library")
    id("mocksy.android.hilt")
    id("mocksy.spotless")
    id("kotlinx-serialization")
}

android {
    namespace = "com.kth.mocksy.core.data"
}

dependencies {
    api(projects.core.model)


    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.retrofit)
    implementation(libs.kotlinx.datetime)
    implementation(libs.okhttp.logging)
}
