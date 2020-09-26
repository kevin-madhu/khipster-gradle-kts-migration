plugins {
    `java-library`
    checkstyle
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

checkstyle {
    // TODO move to variable
    toolVersion = "8.32"
    configFile = file("checkstyle.xml")
    // TODO fix this
    // checkstyleTest.enabled = false
}

tasks.withType<JavaCompile> {
    dependsOn("processResources")
}
