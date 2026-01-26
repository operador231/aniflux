plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.core.ui"
}

dependencies {
    api(libs.lottie.compose)
    api(libs.coil.compose)
    api(libs.androidx.ui.graphics)
}