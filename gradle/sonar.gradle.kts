apply(plugin= "jacoco")

jacoco {
    toolVersion = "0.8.5"
}

tasks {
    getByName<JacocoReport>("jacocoTestReport") {
//        executionData = tasks.withType(Test)
        classDirectories.setFrom(files(sourceSets.main.get().output.classesDirs))
        sourceDirectories.setFrom(files(sourceSets.main.get().java.srcDirs))

        reports {
            xml.isEnabled = true
        }
    }
}


//file("sonar-project.properties").withReader {
//    Properties sonarProperties = new Properties()
//    sonarProperties.load(it)
//
//    sonarProperties.each { key, value ->
//        sonarqube {
//            properties {
//                property key, value
//            }
//        }
//    }
//}

