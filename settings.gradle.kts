pluginManagement {
    plugins {
        id("org.springframework.boot") version "2.2.7.RELEASE"
        id("io.spring.dependency-management") version "1.0.9.RELEASE"
        id("com.google.cloud.tools.jib") version "2.2.0"
        id("com.gorylenko.gradle-git-properties") version "2.2.2"
        id("com.github.node-gradle.node") version "2.2.3"
        id("net.ltgt.apt-eclipse") version "0.21"
        id("net.ltgt.apt-idea") version "0.21"
        id("net.ltgt.apt") version "0.21"
        id("org.liquibase.gradle") version "2.0.2"
        id("org.sonarqube") version "2.8"
        id("io.spring.nohttp") version "0.0.4.RELEASE"
    }
}


rootProject.name = "jhipster"
