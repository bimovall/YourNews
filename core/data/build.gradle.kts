plugins {
    `android-base-lib`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:common"))
    room()
    daggerHilt()
    integrationTest()
    network()
    unitTest()
}