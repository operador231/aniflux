plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.feature.feed.api"
    buildFeatures {
        compose = false
        androidResources {
            enable = false
        }
        viewBinding = false
    }
}

dependencies {
    implementation(project(":core:navigation"))
}