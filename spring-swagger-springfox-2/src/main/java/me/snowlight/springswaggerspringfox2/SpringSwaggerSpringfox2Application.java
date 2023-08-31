package me.snowlight.springswaggerspringfox2;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringSwaggerSpringfox2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSwaggerSpringfox2Application.class, args);
	}

	@Bean
	public Docket api(TypeResolver resolver) {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("me.snowlight"))
				.build()
				.useDefaultResponseMessages(true)
				.additionalModels(resolver.resolve(HelloController.Reservation.class));
	}

}
