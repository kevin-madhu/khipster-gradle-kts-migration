plugins {
    `java-library`
}

val jhipsterDependenciesVersion: String by project

// Use ", version: jhipster_dependencies_version, changing: true" if you want
// to use a SNAPSHOT release instead of a stable release
dependencies {
    implementation(group = "io.github.jhipster", name = "jhipster-framework")

    // import JHipster dependencies BOM
    if (!project.hasProperty("gae")) {
        // TODO check this
        implementation(enforcedPlatform("io.github.jhipster:jhipster-dependencies:$jhipsterDependenciesVersion"))
    }
}
