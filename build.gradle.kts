plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("io.freefair.lombok") version "8.11"
}

group = "io.github.thdudk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "23.0.1"
    modules = listOf("javafx.controls")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("libs/OneGraphLib.jar"))
    implementation("org.apache.commons:commons-math3:3.0")
}

tasks.test {
    useJUnitPlatform()
}
