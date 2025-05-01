plugins {
    id("mocksy.android.library")
    id("mocksy.android.hilt")
}

android {
    namespace = "com.kth.mocksy.core.datastore"
}

dependencies {
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
    implementation(libs.androidx.datastore)
}
