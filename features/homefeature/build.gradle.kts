import Dependencies.daggerHiltProcessorDep
import Dependencies.glideProcessorDep
import Dependencies.hiltProcessorDep

plugins {
    `android-base-dynamic`
}

dependencies {
    kapt(hiltProcessorDep)
    kapt(daggerHiltProcessorDep)
    kapt(glideProcessorDep)
    unitTest()
}