import org.gradle.kotlin.dsl.accessors.runtime.extensionOf

plugins {
    war
}

configure<WarPluginConvention> {
    webAppDirName = "build/resources/main/static/"
}

tasks.withType<War> {
    webXml = file("${project.rootDir}/src/main/webapp/WEB-INF/web.xml")
    isEnabled = true
    extensionOf("war", "war.original")
    setIncludes(listOf("WEB-INF/**", "META-INF/**"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootWar> {
    mainClassName = "com.mycompany.myapp.JhipsterApp"
    setIncludes(listOf("WEB-INF/**", "META-INF/**"))
    webXml = file("${project.rootDir}/src/main/webapp/WEB-INF/web.xml")
}
