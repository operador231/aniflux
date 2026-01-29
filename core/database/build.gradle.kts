plugins {
    id("com.github.operador231.aniflux.library")
}

android {
    namespace = "com.github.operador231.core.database"
}

dependencies {
    api(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.paging)
    testImplementation(libs.androidx.room.testing)
}