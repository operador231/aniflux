plugins {
    id("com.github.operador231.aniflux.library")
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.github.operador231.core.network"

    buildFeatures {
        buildConfig = true
    }
}

apollo {
    service("service") {
        packageName.set("com.github.operador231.core.network.graphql")
        introspection {
            endpointUrl.set("https://shikimori.one/api/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
        mapScalar("PositiveInt", "kotlin.Int")
    }
}

dependencies {
    implementation(project(":core:domain"))

    api(libs.retrofit.core)
    api(libs.retrofit.kotlin.serialization)

    api(platform(libs.okhttp.bom))
    api(libs.okhttp.core)
    api(libs.okhttp.logging)

    implementation(libs.apollo.runtime)
}