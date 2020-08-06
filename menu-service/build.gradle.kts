import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.2.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.palantir.docker") version "0.25.0"
	id("org.sonarqube") version "2.8"
	id("jacoco")
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}
group = "com.food.delivery"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11


sonarqube {
	properties {
		property("sonar.projectKey", "arkadiuszSzast_food-delivery_menu_service")
		property("sonar.organization", "arkadiuszszast")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.login", "539f54cc941bd9f30b6d90ff6482ab039ba5bbe3")
		property("sonar.coverage.exclusions", "**/MenuServiceApplication.java," +
				"**/SwaggerConfiguration.java," +
				"**/FeignClientInterceptor.java")
	}
}

docker {
	name = "${project.name}:${project.version}"
	files("build/libs/${project.name}-${project.version}.jar")
	tag("DockerHub", "szastarek/food-delivery-${project.name}:${project.version}")
}

repositories {
	mavenCentral()
	maven(url = "https://jitpack.io")
}

extra["springCloudVersion"] = "Hoxton.SR6"

dependencies {
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("com.okta.spring:okta-spring-boot-starter:1.4.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.retry:spring-retry")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("com.playtika.reactivefeign:feign-reactor-spring-configuration:2.0.13")
	implementation("com.playtika.reactivefeign:feign-reactor-webclient:2.0.13")
	implementation("com.playtika.reactivefeign:feign-reactor-cloud:2.0.13")
	implementation("com.github.arkadiuszSzast:reactive-logstash-logging-starter:1.0.7")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("io.micrometer:micrometer-registry-prometheus")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("com.playtika.testcontainers:embedded-mongodb:1.78")
	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
