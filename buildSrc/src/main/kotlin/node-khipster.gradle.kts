plugins {
    id("com.github.node-gradle.node")
}

if (project.hasProperty("nodeInstall")) {
    node {
        version = "12.16.1"
        npmVersion = "6.14.5"
        yarnVersion = "1.22.4"
        download = true
    }
}
