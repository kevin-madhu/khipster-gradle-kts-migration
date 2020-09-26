group = "com.mycompany.myapp"
version = "0.0.1-SNAPSHOT"
description = ""

buildscript {
    val kotlinVersion = "1.3.72"
    val ktlintVersion = "9.0.0"
    val detektVersion = "1.1.0"

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
//        maven { url =  uri("https://repo.spring.io/plugins-release") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
        // TODO update needle here (add logic in khipster)
        //jhipster-needle-gradle-buildscript-dependency - JHipster will add additional gradle build script plugins here
    }
}

plugins {
//    java
    maven
    `maven-publish`

    `java-library-convention-khipster`
    `kotlin-khipster`
    `docker-khipster`
    `sonar-khipster`
    `liquibase-khipster`
    `node-khipster`
    `spring-boot-khipster`
    jhipster
    `profile-config-khipster`
    `war-khipster`
    `zipkin-khipster`
    `testing-khipster`
    `git-properties-khipster`
    `editor-config-khipster`
    //jhipster-needle-gradle-plugins - JHipster will add additional gradle plugins here
}

//jhipster-needle-gradle-apply-from - JHipster will add additional gradle scripts to be applied here

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    //jhipster-needle-gradle-repositories - JHipster will add additional repositories
}

dependencies {
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.dropwizard.metrics:metrics-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("net.logstash.logback:logstash-logback-encoder")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hppc")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("javax.cache:cache-api")
    implementation("org.hibernate:hibernate-core")
    implementation("com.zaxxer:HikariCP")
    implementation("org.apache.commons:commons-lang3")
    implementation("commons-io:commons-io")
    implementation("javax.transaction:javax.transaction-api")
    implementation("org.ehcache:ehcache")
    implementation("org.hibernate:hibernate-jcache")
    implementation("org.hibernate:hibernate-entitymanager")
    implementation("org.hibernate.validator:hibernate-validator")
    testImplementation ("org.testcontainers:mysql")
    implementation("io.jsonwebtoken:jjwt-api")

    if (!project.hasProperty("gae")) {
        runtimeOnly ("io.jsonwebtoken:jjwt-impl")
        runtimeOnly ("io.jsonwebtoken:jjwt-jackson")
    } else {
        implementation("io.jsonwebtoken:jjwt-impl")
        implementation("io.jsonwebtoken:jjwt-jackson")
    }

    implementation("io.springfox:springfox-swagger2") {
        exclude(module = "mapstruct")
    }
    implementation("io.springfox:springfox-bean-validators")
    implementation("mysql:mysql-connector-java")
    implementation("org.mapstruct:mapstruct:1.3.1.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.3.1.Final")
    annotationProcessor ("org.hibernate:hibernate-jpamodelgen:5.4.15.Final")
    annotationProcessor ("org.glassfish.jaxb:jaxb-runtime:2.3.2")
    testImplementation ("com.tngtech.archunit:archunit-junit5-api:0.13.1")
    testRuntimeOnly ("com.tngtech.archunit:archunit-junit5-engine:0.13.1")
    testImplementation ("com.h2database:h2")
    //jhipster-needle-gradle-dependency - JHipster will add additional dependencies here
}

tasks.clean {
    delete("build/resources")
}

// TODO
//wrapper {
//    gradleVersion = "6.4.1"
//}
