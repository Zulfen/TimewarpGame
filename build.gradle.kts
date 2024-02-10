plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.zulfen"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.almasb:fxgl:21")
}

javafx {
    modules("javafx.controls", "javafx.fxml", "javafx.media", "javafx.graphics")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.zulfen.Main")
}


tasks {
    shadowJar {
        archiveFileName.set("Timewarp-$version.jar")
        manifest {
            attributes["Main-Class"] = application.mainClass
        }
    }
}


