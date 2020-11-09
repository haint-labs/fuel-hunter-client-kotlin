import com.google.protobuf.gradle.*

plugins {
    id("java-library")
    idea
    kotlin("jvm") version "1.4.10"
    id("com.google.protobuf") version "0.8.13"
    id("maven-publish")
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io")
    mavenLocal()
}

dependencies {
    protobuf("fuel.hunter:proto:0.0.3-SNAPSHOT")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
//    compileOnly("com.google.protobuf:protobuf-java:3.12.0")
    api("com.google.protobuf:protobuf-javalite:3.12.0")
    api("io.grpc:grpc-kotlin-stub-lite:0.2.1")

//    compileOnly("io.grpc:grpc-stub:1.32.1")
//    compileOnly("io.grpc:grpc-protobuf-lite:1.32.1")
//    compileOnly("io.grpc:grpc-okhttp:1.32.1")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.13.0"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.32.1"
        }

        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.2.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                named("java") {
                    option("lite")
                }
            }

            it.plugins {
                id("grpc") {
                    option("lite")
                }

                id("grpckt") {
                    option("lite")
                }
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("github") {
            group = "fuel.hunter"
            artifactId = "client-kotlin"
            version = "0.0.4-SNAPSHOT"

            from(components["java"])
        }
    }
}
