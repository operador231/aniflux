plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.core.navigation"
    buildFeatures {
        compose = false
        androidResources {
            enable = false
        }
        viewBinding = false
    }
}

dependencies {}