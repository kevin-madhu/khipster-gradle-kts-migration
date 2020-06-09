import org.gradle.internal.os.OperatingSystem

apply plugin: "kotlin" // Required for Kotlin integration
apply plugin: "kotlin-kapt" // Required for annotations processing
apply plugin: "kotlin-spring" // See https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
apply plugin: "kotlin-allopen" // See https://kotlinlang.org/docs/reference/compiler-plugins.html#using-in-gradle
apply plugin: "kotlin-jpa" // See https://kotlinlang.org/docs/reference/compiler-plugins.html#jpa-support

apply plugin: "org.jlleitschuh.gradle.ktlint"

apply plugin: "io.gitlab.arturbosch.detekt"

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlin_version}"
    implementation "org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}"

    kapt "org.mapstruct:mapstruct-processor:${mapstruct_version}"
    kapt "org.hibernate:hibernate-jpamodelgen:${hibernate_version}"
    kapt "org.glassfish.jaxb:jaxb-runtime:${jaxb_runtime_version}"

    testImplementation "org.jetbrains.kotlin:kotlin-test-junit:${kotlin_version}"

    testImplementation "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

[compileKotlin, compileTestKotlin]*.with {
    kotlinOptions {
    	jvmTarget = "1.8"
        javaParameters = true
    	freeCompilerArgs = ["-Xjvm-default=enable"]
    }
}

ktlint {
    //See more options: https://github.com/JLLeitschuh/ktlint-gradle#configuration
    ignoreFailures = true
}

detekt {
    toolVersion = detekt_version
    input = files("src/main/kotlin")
    config = files("detekt-config.yml")
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.xml")
        }
    }
}

if (OperatingSystem.current().isWindows()) {
    bootRun {
        doFirst {
            classpath += files("$buildDir/classes/kotlin/main")
        }
    }
}

//Reformat code before compilation
compileKotlin.dependsOn ktlintFormat


jacocoTestReport {
    // Add Kotlin sources to Jacoco source dirs
    sourceDirectories.from += sourceSets.main.kotlin.srcDirs
}

sonarqube {
    properties {
        property "sonar.kotlin.detekt.reportPaths", detekt.reports.xml.destination
    }
}

check.dependsOn jacocoTestReport
