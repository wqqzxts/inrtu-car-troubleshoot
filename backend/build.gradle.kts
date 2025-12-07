plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "1.9.20"
}

group = "org.car.expert"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    implementation("io.ktor:ktor-server-cors:2.3.3")
    implementation("io.ktor:ktor-server-call-logging:2.3.3")

    implementation("org.drools:drools-core:7.73.0.Final")
    implementation("org.drools:drools-compiler:7.73.0.Final")
    implementation("org.kie:kie-api:7.73.0.Final")
    implementation("org.drools:drools-mvel:7.73.0.Final")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}