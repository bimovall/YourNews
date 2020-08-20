plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Dependencies.materialGoogleDep)
    api(Dependencies.constraintLayoutDep)
    api(Dependencies.fragmentKtx)
    sharedDaggerHilt()
    sharedHiltLifecycle()
}