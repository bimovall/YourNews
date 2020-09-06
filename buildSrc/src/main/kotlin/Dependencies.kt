import Dependencies.appCompatDep
import Dependencies.constraintLayoutDep
import Dependencies.coreKtxDep
import Dependencies.daggerHiltDep
import Dependencies.daggerHiltProcessorDep
import Dependencies.espressoDep
import Dependencies.glideDep
import Dependencies.glideProcessorDep
import Dependencies.gsonConverterDep
import Dependencies.hiltLifecycleDep
import Dependencies.hiltProcessorDep
import Dependencies.junitDep
import Dependencies.lifecycle
import Dependencies.materialGoogleDep
import Dependencies.navigationDynamicDep
import Dependencies.navigationKtxDep
import Dependencies.navigationUiKtx
import Dependencies.okhttpLoggingDep
import Dependencies.playCoreDep
import Dependencies.playCoreKtxDep
import Dependencies.retrofitDep
import Dependencies.roomCompilerDep
import Dependencies.roomDep
import Dependencies.roomKtxDep
import Dependencies.roomTestDep
import Dependencies.runnerDep
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val appCompatDep = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val coreKtxDep = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val kotlinDep = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val constraintLayoutDep = "androidx.constraintlayout:constraintlayout:${Versions.constrainVersion}"
    const val materialGoogleDep = "com.google.android.material:material:${Versions.materialVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"

    //playcore
    const val playCoreDep = "com.google.android.play:core:${Versions.playCoreVersion}"
    const val playCoreKtxDep = "com.google.android.play:core-ktx:${Versions.playCoreKtxVersion}"

    //navigation architecture
    const val navigationKtxDep = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"
    const val navigationDynamicDep = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navVersion}"

    // Lifecycle components
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycleVersion}"
    //const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.archLifecycleVersion}"

    // Room components
    const val roomDep = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKtxDep = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompilerDep = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomTestDep = "androidx.room:room-testing:${Versions.roomVersion}"

    //image loader
    const val glideDep = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideProcessorDep = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    //network
    const val retrofitDep = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val okhttpLoggingDep = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingVersion}"
    const val gsonConverterDep = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverterVersion}"

    // Coroutines
    const val coroutineApi = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutineCoreApi =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // Dagger Hilt
    const val daggerHiltDep = "com.google.dagger:hilt-android:${Versions.daggerHiltVersion}"
    const val daggerHiltProcessorDep = "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltVersion}"
    const val hiltLifecycleDep = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleVersion}"
    const val hiltProcessorDep = "androidx.hilt:hilt-compiler:${Versions.hiltLifecycleVersion}"

    //integration test
    const val runnerDep = "androidx.test:runner:${Versions.runnerVersion}"
    const val espressoDep = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    //unit test
    const val junitDep = "junit:junit:${Versions.junitVersion}"


}

fun DependencyHandler.network() {
    implementation(retrofitDep)
    implementation(okhttpLoggingDep)
    implementation(gsonConverterDep)
}

fun DependencyHandler.playCore() {
    implementation(playCoreDep)
    implementation(playCoreKtxDep)
}

fun DependencyHandler.design() {
    implementation(constraintLayoutDep)
    implementation(materialGoogleDep)
}

fun DependencyHandler.sharedDesign() {
    api(constraintLayoutDep)
    api(materialGoogleDep)
}

fun DependencyHandler.daggerHilt() {
    implementation(daggerHiltDep)
    kapt(daggerHiltProcessorDep)
}

fun DependencyHandler.sharedDaggerHilt() {
    api(daggerHiltDep)
    kapt(daggerHiltProcessorDep)
}

fun DependencyHandler.hiltLifecycle() {
    implementation(hiltLifecycleDep)
    kapt(hiltProcessorDep)
}

fun DependencyHandler.sharedHiltLifecycle() {
    api(hiltLifecycleDep)
    kapt(hiltProcessorDep)
}

fun DependencyHandler.appCompat() {
    implementation(appCompatDep)
    implementation(coreKtxDep)
}


fun DependencyHandler.sharedAppCompat() {
    api(appCompatDep)
    api(coreKtxDep)
}

fun DependencyHandler.lifecycleComponent() {
    implementation(lifecycle)
}

fun DependencyHandler.navigationArch() {
    implementation(navigationKtxDep)
    implementation(navigationUiKtx)
    implementation(navigationDynamicDep)
}

fun DependencyHandler.sharedNavigationArch() {
    api(navigationKtxDep)
    api(navigationUiKtx)
    api(navigationDynamicDep)
}

fun DependencyHandler.glide() {
    implementation(glideDep)
    kapt(glideProcessorDep)
}

fun DependencyHandler.room() {
    implementation(roomDep)
    implementation(roomKtxDep)
    kapt(roomCompilerDep)
    androidTestImplementation(roomTestDep)
}

fun DependencyHandler.integrationTest() {
    androidTestImplementation(runnerDep)
    androidTestImplementation(espressoDep)
}

fun DependencyHandler.unitTest() {
    testImplementation(junitDep)
}

private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

private fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

private fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}