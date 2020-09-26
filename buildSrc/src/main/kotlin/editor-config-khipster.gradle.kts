plugins {
    `java-library`
    idea
    eclipse
    id("net.ltgt.apt-eclipse")
    id("net.ltgt.apt-idea")
    id("net.ltgt.apt")
}

idea {
    module {
        excludeDirs.add(File("build/generated/sources/annotationProcessor/java/main"))
        excludeDirs.add(File("build/generated/sources/annotationProcessor/kotlin/main"))
    }
}

eclipse {
    sourceSets {
        main {
            java {
                srcDirs("build/generated/sources/annotationProcessor/java/main", "build/generated/sources/annotationProcessor/kotlin/main")
            }
        }
    }
}
