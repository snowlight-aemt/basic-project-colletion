plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
//	// TODO class 의 지정한 클래스가 상속이 가능하도록 해주는 플러그인 (상속)
//	id("org.jetbrains.kotlin.plugin.allopen") version "1.9.21"
//	// TODO Class 에 기본 생성자를 추가해주는 플러그인 (생성자)
//	kotlin("plugin.noarg") version "1.9.21"

	// TODO Spring 관련 모든 애노테이션을 추가해주는 플러그인
	kotlin("plugin.spring") version "1.9.21"
	// TODO JPA 관련 모든 애노테이션을 추가해주는 플러그인
	kotlin("plugin.jpa") version "1.9.21"
}


// TODO 명시한 어노테이션이 붙어 있는 모든 클래스에 open 를 붙여준다. (상속)
//allOpen {
//	annotations("org.springframework.boot.autoconfigure.SpringBootApplication")
//}

// TODO 명시한 어노테이션이 붙어 있는 모든 클래스에 기본 생성자를 추가해 준다. (생성자)
//noArg {
//	annotations("jakarta.persistence.Entity")
//}


group = "me.snowlight"
version = "0.0.1-SNAPSHOT"

java {
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(17)
}