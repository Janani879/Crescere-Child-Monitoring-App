buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.chaquo.python") version "16.0.0" apply false
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}