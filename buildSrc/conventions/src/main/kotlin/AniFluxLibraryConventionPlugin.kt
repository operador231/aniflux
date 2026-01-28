package com.github.operador231.aniflux.library

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AniFluxLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                compileSdk {
                    version = release(36)
                }

                buildFeatures {
                    compose = true
                }

                defaultConfig {
                    minSdk {
                        version = release(34)
                    }

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    val proguardFile = file("consumer-rules.pro")
                    if (proguardFile.exists()) {
                        consumerProguardFiles(proguardFile.name)
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.addAll(
                        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                        "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
                        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                        "-Xexplicit-api=warning",
                        "-Xcontext-parameters",
                        "-progressive"
                    )
                }
            }

            dependencies {
                // Compose BOM & Core
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("androidTestImplementation", platform(bom))

                //add("api", libs.findLibrary("androidx-compose-material3").get())
                add("api", "androidx.compose.material3:material3:1.5.0-alpha12")
                add("api", libs.findLibrary("androidx-compose-ui").get())
                add("api", "androidx.compose.material3:material3-adaptive-navigation-suite:1.3.0-alpha06")
                add("api", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

                // Android Standard
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-appcompat").get())
                add("implementation", libs.findLibrary("material").get())

                // Logging
                add("implementation", libs.findLibrary("timber").get())

                // Hilt
                add("implementation", libs.findLibrary("hilt-android").get())
                add("ksp", libs.findLibrary("hilt-compiler").get())
                add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())

                // Serialization
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

                // Navigation Runtime
                add("implementation", libs.findLibrary("androidx-navigation-common-ktx").get())
                add("implementation", libs.findLibrary("androidx-navigation-runtime-ktx").get())

                // Immutable collections
                add("implementation", libs.findLibrary("kotlinx-collections-immutable").get())

                // Testing
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
            }
        }
    }
}