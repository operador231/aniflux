plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.feature.feed.impl"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:feed:api"))

    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)
}