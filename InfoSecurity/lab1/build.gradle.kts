plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

dependencies {
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.javalin:javalin:6.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.10")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = false
        csv.required = false
        html.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

