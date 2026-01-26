plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation("com.android.tools.build:gradle:9.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.3.0")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.3.4")
}

gradlePlugin {
    plugins {
        register("anifluxLibrary") {
            id = "com.github.operador231.aniflux.library"
            implementationClass = "com.github.operador231.aniflux.library.AniFluxLibraryConventionPlugin"
        }
    }
}