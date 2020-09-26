plugins {
    `kotlin-dsl`
    jacoco
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven { url =  uri("https://repo.spring.io/plugins-release") }
    maven { url =  uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    val jibPluginVersion: String by project
    val sonarqubePluginVersion: String by project
    val liquibasePluginVersion: String by project
    val aptPluginVersion: String by project
    val gitPropertiesPluginVersion: String by project
    val gradleNodePluginVersion: String by project
    val springBootVersion: String by project
    val springDependencyManagementPluginVersion: String by project
    val springNoHttpPluginVersion: String by project

    implementation("org.springframework.boot", "spring-boot-gradle-plugin", springBootVersion)
    implementation("io.spring.gradle", "dependency-management-plugin", springDependencyManagementPluginVersion)
    implementation("io.spring.nohttp", "nohttp-gradle", springNoHttpPluginVersion)

    implementation("com.google.cloud.tools", "jib-gradle-plugin", jibPluginVersion)
    implementation("org.sonarsource.scanner.gradle","sonarqube-gradle-plugin", sonarqubePluginVersion)
    implementation("org.liquibase", "liquibase-gradle-plugin", liquibasePluginVersion)
    implementation("net.ltgt.gradle", "gradle-apt-plugin", aptPluginVersion)
    implementation("gradle.plugin.com.gorylenko.gradle-git-properties", "gradle-git-properties", gitPropertiesPluginVersion)
    implementation("com.github.node-gradle", "gradle-node-plugin", gradleNodePluginVersion)
}
