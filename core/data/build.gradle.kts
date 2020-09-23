plugins {
    `android-base-lib`
}

android {
    sourceSets["androidTest"].java.srcDirs("src/sharedTest/java")
    sourceSets["test"].java.srcDirs("src/sharedTest/java")
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