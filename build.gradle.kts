import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        maven { url =  uri("https://repo.spring.io/plugins-release") }
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
    java
    maven
    `maven-publish`
    idea
    jacoco
    eclipse
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
    //jhipster-needle-gradle-plugins - JHipster will add additional gradle plugins here
}

/*
apply("kotlin")
apply(from = "gradle/sonar.gradle.kts")
apply(from = "gradle/docker.gradle.kts")
*/

//jhipster-needle-gradle-apply-from - JHipster will add additional gradle scripts to be applied here
//if (project.hasProperty("prod") || project.hasProperty("gae")) {
//    apply("gradle/profile_prod.gradle.kts")
//} else {
//    apply("gradle/profile_dev.gradle.kts")
//}

// if (project.hasProperty("war")) {
//     apply("gradle/war.gradle.kts")
// }

if (project.hasProperty("gae")) {
    apply(plugin = "maven")
    apply(plugin = "org.springframework.boot.experimental.thin-launcher")
    apply(plugin = "io.spring.dependency-management")

    dependencyManagement {
        val jhipsterDependenciesVersion = "3.7.1"
        imports {
            mavenBom("io.github.jhipster:jhipster-dependencies:${jhipsterDependenciesVersion}")
        }
    }

//    appengineStage dependsOn thinResolve
}

idea {
    module {
        excludeDirs.add(File("build/generated/sources/annotationProcessor/java/main"))
        excludeDirs.add(File("build/generated/sources/annotationProcessor/kotlin/main"))
    }
}


eclipse {
    sourceSets {
        main {
            java {
                srcDirs("build/generated/sources/annotationProcessor/java/main", "build/generated/sources/annotationProcessor/kotlin/main")
            }
        }
    }
}

defaultTasks = mutableListOf("bootRun")

springBoot {
    mainClassName = "com.mycompany.myapp.JhipsterApp"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*IT*", "**/*IntTest*")

    testLogging {
        events("FAILED", "SKIPPED")
    }

    // uncomment if the tests reports are not generated
    // see https://github.com/jhipster/generator-jhipster/pull/2771 and https://github.com/jhipster/generator-jhipster/pull/4484
    // ignoreFailures = true

    reports {
        html.isEnabled = true
    }
}

tasks.register<Test>("integrationTest") {
    useJUnitPlatform()
    description = "Execute integration tests."
    group = "verification"
    include("**/*IT*", "**/*IntTest*")

    testLogging {
        events("FAILED", "SKIPPED")
    }

    if(project.hasProperty("testcontainers")) {
        environment("spring.profiles.active", "testcontainers")
    }

    // uncomment if the tests reports are not generated
    // see https://github.com/jhipster/generator-jhipster/pull/2771 and https://github.com/jhipster/generator-jhipster/pull/4484
    // ignoreFailures = true

    reports {
        html.isEnabled = false
    }
}

tasks.register<TestReport>("testReport") {
    destinationDir = file("$buildDir/reports/tests")
    reportOn("test")
}

tasks.register<TestReport>("integrationTestReport") {
    destinationDir = file("$buildDir/reports/tests")
    // TODO verify this
    reportOn("integrationTest")
}

//assert System.properties["java.specification.version"] == "1.8" || "11" || "12" || "13" || "14"

//check.dependsOn integrationTest

if (!project.hasProperty("runList")) {
    project.extra["runList"] = "main"
}

project.extra["diffChangelogFile"] = "src/main/resources/config/liquibase/changelog/${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}_changelog.xml"


liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "driver" to "org.h2.Driver",
            "url" to "jdbc:h2:file:./build/h2db/db/jhipster",
            "username" to "jhipster",
            "password" to "",
            "changeLogFile" to "src/main/resources/config/liquibase/master.xml",
            "defaultSchemaName" to "",
            "logLevel" to "debug",
            "classpath" to "src/main/resources/"
        )
    }
    activities.register("difflog") {
        arguments = mapOf(
            "driver" to "org.h2.Driver",
            "url" to "jdbc:h2:file:./build/h2db/db/jhipster",
            "username" to "jhipster",
            "password" to "",
            "changeLogFile" to project.extra["diffChangelogFile"],
            "referenceUrl" to "hibernate:spring:com.mycompany.myapp.domain?dialect=org.hibernate.dialect.H2Dialect&hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy&hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
            "defaultSchemaName" to "",
            "logLevel" to "debug",
            "classpath" to "$buildDir/classes/java/main"
        )
    }
    runList = project.ext["runList"]
}

gitProperties {
    failOnNoGitDirectory = false
    keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe")
}

checkstyle {
    // TODO move to variable
    toolVersion = "8.32"
    configFile = file("checkstyle.xml")
    // TODO fix this
    // checkstyleTest.enabled = false
}

nohttp {
    source.setIncludes(mutableSetOf("build.gradle.kts", "README.md"))
}

configurations {
    // TODO
    // providedRuntime
    implementation {
        exclude(module = "spring-boot-starter-tomcat")
    }
    all {
        resolutionStrategy {
            // Inherited version from Spring Boot can't be used because of regressions:
            // To be removed as soon as spring-boot use the same version
            force("org.liquibase:liquibase-core:3.9.0")
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    //jhipster-needle-gradle-repositories - JHipster will add additional repositories
}

dependencies {
    // import JHipster dependencies BOM
    if (!project.hasProperty("gae")) {
        // TODO check this
        implementation(enforcedPlatform("io.github.jhipster:jhipster-dependencies:3.7.1"))
    }

    // Use ", version: jhipster_dependencies_version, changing: true" if you want
    // to use a SNAPSHOT release instead of a stable release
    implementation(group = "io.github.jhipster", name = "jhipster-framework")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.springframework.boot:spring-boot-starter-cache")
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
    implementation("org.liquibase:liquibase-core")
    liquibaseRuntime ("org.liquibase:liquibase-core")
    liquibaseRuntime ("org.liquibase.ext:liquibase-hibernate5:3.8")
//    liquibaseRuntime (sourceSets.main.compileClasspath)
    implementation("org.springframework.boot:spring-boot-loader-tools")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation ("org.testcontainers:mysql")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web") {
            exclude (module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.zalando:problem-spring-web")
    implementation("org.springframework.boot:spring-boot-starter-cloud-connectors")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-data")
    implementation("org.springframework.security:spring-security-web")
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
    liquibaseRuntime( "mysql:mysql-connector-java")
    implementation("org.mapstruct:mapstruct:1.3.1.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.3.1.Final")
    annotationProcessor ("org.hibernate:hibernate-jpamodelgen:5.4.15.Final")
    annotationProcessor ("org.glassfish.jaxb:jaxb-runtime:2.3.2")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:2.2.7.RELEASE")
    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation ("org.springframework.security:spring-security-test")
    testImplementation ("org.springframework.boot:spring-boot-test")
    testImplementation ("com.tngtech.archunit:archunit-junit5-api:0.13.1")
    testRuntimeOnly ("com.tngtech.archunit:archunit-junit5-engine:0.13.1")
    testImplementation ("com.h2database:h2")
    liquibaseRuntime ("com.h2database:h2")
    //jhipster-needle-gradle-dependency - JHipster will add additional dependencies here
}

if (project.hasProperty("gae")) {

    tasks.withType<GenerateMavenPom> {
        val basePath = "build/resources/main/META-INF/maven"
        pom.withXml(dependencyManagement.pomConfigurer)
        destination = file("$basePath/${project.group}/${project.name}/pom.xml")
    }

    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        dependsOn("createPom")
    }
}


tasks.clean {
    delete("build/resources")
}

// TODO
//wrapper {
//    gradleVersion = "6.4.1"
//}

if (project.hasProperty("nodeInstall")) {
    node {
        version = "12.16.1"
        npmVersion = "6.14.5"
        yarnVersion = "1.22.4"
        download = true
    }
}

tasks.withType<JavaCompile> {
    dependsOn("processResources")
}

tasks.withType<ProcessResources> {
    dependsOn("bootBuildInfo")
}
