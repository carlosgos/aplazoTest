package com.aplazo.pagos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
/**
 * clase de inicio de api rest /api/aplazo
 * @author juan carlos
 * @version 1
 */
@SpringBootApplication
@ComponentScan({"com.*"})
@EntityScan("com.aplazo.model")
@EnableJpaRepositories("com.aplazo.repository")
public class AplazoTestApplication extends SpringBootServletInitializer{
	
	private static final Logger log = LogManager.getLogger(AplazoTestApplication.class);

	
	
	public static void main(String[] args) {
		SpringApplication.run(AplazoTestApplication.class, args);
	}
	@Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(AplazoTestApplication.class);
	    }
	/**
	 * funcion para validar y ver en consola los beans creados para el contexto
	 * @param ctx ApplicationContext
	 * @return 
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			log.info("Api rest aplazo beans");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			log.info("size: {}",beanNames.length);
			for (String beanName : beanNames) {
				log.info("nombre: {}",beanName);
			}

		};
	}
	

}
