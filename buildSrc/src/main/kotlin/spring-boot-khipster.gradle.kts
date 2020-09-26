plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.spring.nohttp")
}

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

springBoot {
    mainClassName = "com.mycompany.myapp.JhipsterApp"
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

tasks.withType<ProcessResources> {
    dependsOn("bootBuildInfo")
}

project.defaultTasks = mutableListOf("bootRun")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-loader-tools")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:2.2.7.RELEASE")
    testImplementation ("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation ("org.springframework.security:spring-security-test")
    testImplementation ("org.springframework.boot:spring-boot-test")
}
