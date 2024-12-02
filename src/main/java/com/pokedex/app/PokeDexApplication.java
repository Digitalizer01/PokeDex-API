package com.pokedex.app;

import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.pokedex" })
@EnableJpaRepositories(basePackages = "com.pokedex.repository")
@EntityScan(basePackages = "com.pokedex.model")
public class PokeDexApplication {

	public static void main(String[] args) {
		// Determina el directorio actual del WAR
        String currentDir = Paths.get("").toAbsolutePath().toString();
        
        // Pasa la ruta del archivo application.properties como argumento
        System.setProperty("spring.config.location", "file:" + currentDir + "/application.properties");
        
		SpringApplication.run(PokeDexApplication.class, args);
	}

}
