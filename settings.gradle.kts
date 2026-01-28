pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AniFlux"
include(":app")
include(":core:ui")
include(":core:navigation")
include(":feature:feed:impl")
include(":feature:catalog:api")
include(":feature:catalog:impl")
include(":feature:feed:api")
include(":core:domain")
include(":core:network")
