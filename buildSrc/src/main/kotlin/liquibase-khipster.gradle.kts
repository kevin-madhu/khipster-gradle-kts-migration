import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    `java-library`
    id("org.liquibase.gradle")
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

    if (!project.hasProperty("runList")) {
        project.extra["runList"] = "main"
    }

    runList = project.ext["runList"]
}

dependencies {
    implementation("org.liquibase:liquibase-core")

    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate5:3.8")
    liquibaseRuntime( "mysql:mysql-connector-java")
    liquibaseRuntime("com.h2database:h2")
}
