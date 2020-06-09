val developmentOnly = configurations.create("developmentOnly")
var profiles = "dev"

configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.h2database.h2")
}

if (project.hasProperty("no-liquibase")) {
    profiles += ",no-liquibase"
}
if (project.hasProperty("tls")) {
    profiles += ",tls"
}

springBoot {
    buildInfo {
        properties {
            time = null
        }
    }
}


//bootRun {
//    args = []
//}

tasks.named("webpack", com.moowork.gradle.node.npm.NpmTask::class.java) {
    inputs.files("package-lock.json", "build.gradle.kts")
    inputs.dir("src/main/webapp/")

    val webpackDevFiles = fileTree("webpack//")
    webpackDevFiles.exclude("webpack.prod.js")
    inputs.files(webpackDevFiles)

    outputs.dir("build/resources/main/static/")
    dependsOn("npmInstall")

    setArgs(mutableListOf("run", "webpack:build"))

    setEnvironment(mutableMapOf("APP_VERSION" to project.version))
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)
    inputs.property("springProfiles", profiles)

    filesMatching("**/application.yml") {
        filter {
            it.replace("#project.version#", version.toString())
        }
        filter {
            it.replace("#spring.profiles.active#", profiles)
        }
    }

    dependsOn("webpack")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    dependsOn("processResources")
}
