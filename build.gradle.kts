// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        // other repositories...
        mavenCentral()
        google()

    }
    dependencies {
        // other plugins...
        classpath (libs.hilt.android.gradle.plugin)

    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
}