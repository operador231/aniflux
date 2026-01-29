plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.tink.android)
}