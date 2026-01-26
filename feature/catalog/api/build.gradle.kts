plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.feature.catalog.api"
    buildFeatures { // todo: use another plugin for api modules
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