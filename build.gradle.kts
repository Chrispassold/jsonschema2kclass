plugins {
    kotlin("jvm") version "1.8.0"
    application
}

val mainClassName = "com.chrispassold.jsonschema2kclass.MainKt"

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin")
        resources.srcDirs("src/main/resources")
    }
    test {
        kotlin.srcDirs("src/test/kotlin")
        resources.srcDirs("src/test/resources")
    }
}

tasks.jar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to mainClassName,
            "Implementation-Title" to "My Application",
            "Implementation-Version" to "1.0.0"
        )
    }
    from(sourceSets.main.get().output)
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("${project.name}-assembly")
    manifest = tasks.jar.get().manifest
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
}

application {
    mainClass.set(mainClassName)
}

repositories {
    mavenCentral()
}
