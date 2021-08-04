package com.mazzee.dts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.OAS_30)
				.apiInfo(new ApiInfoBuilder().title("Tailor shop API").description("API for tailor shop")
						.contact(new Contact("Mazharuddin Shaikh", "test.dts.com", "test.mazhar@gmail.com"))
						.version("1.0.0").license("Open licence free for all").build())
				.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
		return docket;
	}

}
