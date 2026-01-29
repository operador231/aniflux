plugins {
    id("com.github.operador231.aniflux.library")
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.github.operador231.feature.catalog.impl"
}

dependencies {
    implementation(project(":feature:catalog:api"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))

    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)

    // Paging 3
    implementation(libs.androidx.paging.compose)

    // Apollo
    implementation(libs.apollo.runtime)
}