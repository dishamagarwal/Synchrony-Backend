package com.synchrony.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.h2.server.web.WebServlet;
// import org.springframework.boot.context.embedded.ServletRegistrationBean;

@SpringBootApplication
public class SpringApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringApp.class, args);
	}

	// @Bean
	// public ServletRegistrationBean h2servletRegistration() {
	// 	ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	// 	registration.addUrlMappings("/console/*");
	// 	return registration;
	// }
}
