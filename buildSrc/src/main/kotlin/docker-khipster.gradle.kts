import java.nio.file.Paths

plugins {
    id("com.google.cloud.tools.jib")
}

jib {
    from {
        image = "adoptopenjdk:11-jre-hotspot"
    }

    to {
        image = "accounting:latest"
    }

    container {
        entrypoint = listOf("bash", "-c", "/entrypoint.sh")
        ports = listOf("9012", "5201/udp")
        environment = mapOf("SPRING_OUTPUT_ANSI_ENABLED" to "ALWAYS", "JHIPSTER_SLEEP" to "0")
        creationTime = "USE_CURRENT_TIMESTAMP"
    }

    extraDirectories {
        setPaths(Paths.get("src/main/jib"))
        permissions = mapOf("/entrypoint.sh" to "755")
    }
}
