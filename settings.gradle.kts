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

rootProject.name = "parse"
include(":app")
include(":core:ui")
include(":core:common")
include(":feature:navigation")
include(":feature:home:presentation")
include(":feature:books:presentation")
include(":feature:books:domain")
include(":feature:settings:presentation")
include(":data:settings:api")
include(":data:settings:impl:datastore")
include(":feature:settings:domain")
include(":feature:book:presentation")
include(":domain:usecase")
include(":domain:models")
include(":feature:book:domain")
include(":feature:book:data")
