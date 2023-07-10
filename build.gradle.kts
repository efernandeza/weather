// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
