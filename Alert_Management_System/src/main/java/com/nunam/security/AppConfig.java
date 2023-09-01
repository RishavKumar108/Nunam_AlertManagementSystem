package com.nunam.security;



import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {
	

	@Bean
	 SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

				
		http.securityMatchers((matchers) -> matchers
				.requestMatchers("*")
			).authorizeHttpRequests((authorizeHttpRequests) ->
		authorizeHttpRequests
		.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**","/api/auth","/api/register","/api/notify").permitAll()
		.and()
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
	)
		
		.sessionManagement((sessionManagement) ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)
		.csrf((csrf) -> csrf.disable())
		
		.httpBasic();

		return http.build();
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	        .components(new Components()
	            .addSecuritySchemes("basicAuth", new SecurityScheme()
	                .type(SecurityScheme.Type.HTTP)
	                .scheme("basic"))
	            .addSecuritySchemes("bearer-jwt", new SecurityScheme()
	                .type(SecurityScheme.Type.HTTP)
	                .scheme("bearer")
	                .bearerFormat("JWT")
	                .in(SecurityScheme.In.HEADER)
	                .name("Authorization")))
	        .info(new Info()
	            .title("Nunam Alert Management System RestApi")
	            .version("1.1")
	            .description("Alert Management System with Spring Security")
	            .termsOfService("8080/swagger-ui/index.html"))
	        .addSecurityItem(new SecurityRequirement()
	            .addList("basicAuth", Arrays.asList("read", "write"))
	            .addList("bearer-jwt", Arrays.asList("read", "write")));
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}