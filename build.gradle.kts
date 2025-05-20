plugins {
    kotlin("jvm") version "2.1.10"
    id("application")
}

group = "PA_Project"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
}

tasks.test {
    useJUnit()
}

application {
    mainClass.set("PA_Project.MainPhase3Kt")
}

kotlin {
    jvmToolchain(23)
}