plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            "-Xexplicit-api=warning",
            "-Xcontext-parameters",
            "-progressive"
        )
    }
}

dependencies {
    // Collections
    implementation(libs.kotlinx.collections.immutable)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Paging 3
    implementation(libs.androidx.paging.common)
}
