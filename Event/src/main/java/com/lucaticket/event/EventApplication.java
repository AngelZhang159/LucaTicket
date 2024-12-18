package com.lucaticket.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @since 11-12-2024
 * Clase principal de la aplicación Event.
 * 
 * Esta clase inicializa y ejecuta la aplicación de eventos utilizando Spring Boot.
 * Además, habilita la capacidad de descubrimiento de servicios en un entorno de microservicios.
 * 
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EventApplication {

	  /**
     * Método principal que inicia la ejecución de la aplicación.
     *
     * @param args argumentos de la línea de comandos.
     */
	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
	}

}
