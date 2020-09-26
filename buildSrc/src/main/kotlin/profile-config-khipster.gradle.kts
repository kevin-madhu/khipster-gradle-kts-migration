plugins {
    `java-library`
    id("org.springframework.boot")
//    id("com.github.node-gradle.node")
}

if (project.hasProperty("prod") || project.hasProperty("gae")) {
//    dependencies {
//        testImplementation("com.h2database:h2")
//    }
//
//    var profiles = "prod"
//    if (project.hasProperty("no-liquibase")) {
//        profiles += ",no-liquibase"
//    }
//
//    if (project.hasProperty("swagger")) {
//        profiles += ",swagger"
//    }
//
//    springBoot {
//        buildInfo()
//    }
//
////bootRun {
////    args = []
////}
//
//    tasks.named("webpack_test", com.moowork.gradle.node.npm.NpmTask::class.java) {
//        setArgs(listOf("run", "webpack:test"))
//        dependsOn("npmInstall")
//    }
//
//    tasks.named("webpack", com.moowork.gradle.node.npm.NpmTask::class.java) {
//        setArgs(listOf("run", "webpack:prod"))
//        setEnvironment(mutableMapOf("APP_VERSION" to project.version))
//    }
//
//    tasks.withType<ProcessResources> {
//        inputs.property("version", version)
//        inputs.property("springProfiles", profiles)
//
//        filesMatching("**/application.yml") {
//            filter {
//                it.replace("#project.version#", version.toString())
//            }
//            filter {
//                it.replace("#spring.profiles.active#", profiles)
//            }
//        }
//
//        dependsOn("webpack")
//    }
//
//
//    tasks.withType<Test> {
//        dependsOn("webpack_test")
//    }
//
//    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
//        dependsOn("processResources")
//    }
} else {
//    val developmentOnly = configurations.create("developmentOnly")
//    var profiles = "dev"
//
//    configurations {
//        developmentOnly
//        runtimeClasspath {
//            extendsFrom(developmentOnly)
//        }
//    }
//
//    dependencies {
//        developmentOnly("org.springframework.boot:spring-boot-devtools")
//        implementation("com.h2database:h2")
//    }
//
//    if (project.hasProperty("no-liquibase")) {
//        profiles += ",no-liquibase"
//    }
//    if (project.hasProperty("tls")) {
//        profiles += ",tls"
//    }
//
//    springBoot {
//        buildInfo {
//            properties {
//                time = null
//            }
//        }
//    }
//
//
////bootRun {
////    args = []
////}
//
//    tasks.named("webpack", com.moowork.gradle.node.npm.NpmTask::class.java) {
//        inputs.files("package-lock.json", "build.gradle.kts")
//        inputs.dir("src/main/webapp/")
//
//        val webpackDevFiles = fileTree("webpack//")
//        webpackDevFiles.exclude("webpack.prod.js")
//        inputs.files(webpackDevFiles)
//
//        outputs.dir("build/resources/main/static/")
//        dependsOn("npmInstall")
//
//        setArgs(mutableListOf("run", "webpack:build"))
//
//        setEnvironment(mutableMapOf("APP_VERSION" to project.version))
//    }
//
//    tasks.withType<ProcessResources> {
//        inputs.property("version", version)
//        inputs.property("springProfiles", profiles)
//
//        filesMatching("**/application.yml") {
//            filter {
//                it.replace("#project.version#", version.toString())
//            }
//            filter {
//                it.replace("#spring.profiles.active#", profiles)
//            }
//        }
//
//        dependsOn("webpack")
//    }
//
//    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
//        dependsOn("processResources")
//    }
}
