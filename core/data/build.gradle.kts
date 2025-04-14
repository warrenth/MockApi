plugins {
    id("mockapi.android.library")
    id("mockapi.android.hilt")
    id("mockapi.spotless")
}

android {
    namespace = "com.kth.mockapi.core.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.network)
}
