plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    room()
    daggerHilt()
    integrationTest()
    network()
}