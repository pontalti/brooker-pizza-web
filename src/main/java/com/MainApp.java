package com;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.config.GracefulShutdown;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Pontalti X
 *
 */
@SuppressWarnings("deprecation")
@SpringBootApplication
@ComponentScan("com")
@Configuration
@EnableSwagger2
public class MainApp extends SpringBootServletInitializer {
    
	public MainApp() {
		super();
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApp.class);
    }

	
	@Bean
	public Docket greetingApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.controller"))
					.build()
					.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
					.title("Spring Boot REST API")
					.description("\"Spring Boot REST API for Credit Suisse code task\"")
					.version("1.0.0")
					.license("Apache License Version 2.0")
					.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
					.build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}
	
	@Bean
	public GracefulShutdown gracefulShutdown() {
	    return new GracefulShutdown();
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(gracefulShutdown);
	    return factory;
	}
	
	@Bean
	public ServletRegistrationBean<WebServlet> h2servletRegistration(){
        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<WebServlet>(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
	
	@Bean
    public WebMvcConfigurer configurer () {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
        		registry.addResourceHandler("swagger-ui.html")
						.addResourceLocations("classpath:/META-INF/resources/");
				registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }

}
