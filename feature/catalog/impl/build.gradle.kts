plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.feature.catalog.impl"
}

dependencies {
    implementation(project(":feature:catalog:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))

    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)
}