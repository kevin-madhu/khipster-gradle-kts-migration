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
