pluginManagement {
    repositories {
        google() // This repository is required for Firebase and Android plugins
        mavenCentral() // For other dependencies
        gradlePluginPortal() // For Gradle plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // This enforces repositories defined in settings
    repositories {
        google() // Required for Firebase and Android dependencies
        mavenCentral() // Add Maven Central for other dependencies
    }
}

rootProject.name = "MY"
include(":app") // Include your app module
