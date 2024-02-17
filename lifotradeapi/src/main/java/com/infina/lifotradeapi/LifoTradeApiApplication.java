package com.infina.lifotradeapi;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.models.Components;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
@EnableTransactionManagement
@OpenAPI30
public class LifoTradeApiApplication {
	
	public static void main(String[] args) {
		Dotenv.configure().directory("lifotradeapi/src/main/resources").systemProperties().load();
		SpringApplication.run(LifoTradeApiApplication.class, args);
        		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("not: "
			+ "'/api/employee/me' de 'Authorization' "
			+ "parametresine ne verirseniz verin swaggerda tanımladığımız tokenı gönderiyor.")
	        String description,
			                     @Value("v1") String version) {
	    final String securitySchemeName = "bearerAuth";						
		return new OpenAPI()
	            .components(
	                    new Components()
	                            .addSecuritySchemes(securitySchemeName,
	                                    new SecurityScheme()
	                                            .type(SecurityScheme.Type.HTTP)
	                                            .scheme("bearer")
	                                            .bearerFormat("JWT")
	                            )
	            )
	            .security(List.of(new SecurityRequirement().addList(securitySchemeName))).info(new Info()
				.title("LiFoTrade Api")
				.version(version)
				.description(description)
				.license(new License().name("LiFoTrade Api Licence")));	
	}

}
