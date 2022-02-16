package com.salesianostriana.dam;

import com.salesianostriana.dam.config.StorageProperties;
import com.salesianostriana.dam.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner init (StorageService storageService){
		return args -> {
			storageService.deleteAll();
			storageService.init();

		};
	}

}
