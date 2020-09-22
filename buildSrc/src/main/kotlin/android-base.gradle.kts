plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    //id("dagger.hilt.android.plugin")
    //id("androidx.navigation.safeargs.kotlin")
}
apply(plugin  = "dagger.hilt.android.plugin")
apply(plugin  = "androidx.navigation.safeargs.kotlin")