plugins {
	java
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.streaming"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()

	// GitHub Packages repository for streaming-library
	maven {
		url = uri(
			"https://maven.pkg.github.com/stregea/streaming_library"
		)
		credentials {
			username = System.getenv("USERNAME")

			password = System.getenv("TOKEN")
		}
	}
}

dependencies {

	implementation("com.streaming:streaming-library:0.1.0")

	implementation("org.springframework.boot:spring-boot-starter-kafka")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	// Allows for serializing objects.
	implementation("com.fasterxml.jackson.core:jackson-databind")
 	// Allows for serializing Instant objects.
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
