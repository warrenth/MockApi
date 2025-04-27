plugins {
    id("mocksy.android.library")
    id("mocksy.android.hilt")
    id("mocksy.spotless")
}

android {
    namespace = "com.kth.mocksy.core.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.network)
}
