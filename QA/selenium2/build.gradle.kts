plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "r3nny"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.7.0")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.0")

    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-firefox-driver
    implementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    implementation("org.seleniumhq.selenium:selenium-firefox-driver:4.18.1")

}

tasks.test {
    useTestNG()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}