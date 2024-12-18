package com.lucaticket.event.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Angel
 * @version 1.0
 * @since 16-12-2024
 * Configuración de OpenAPI para la documentación del microservicio de eventos.
 * 
 * Esta clase define la configuración para la documentación de la API utilizando OpenAPI 3. 
 * Incluye información del proyecto, contacto y servidores disponibles.
 * 
 */

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Grupo 2" 
						),
				title = "LucaTicket Spring",
				description = "Documentación del projecto 3 [LucaTicket], microservicio de EVENTO",
				version = "1.0"
				),
		servers = {
				@Server(
						description = "Local",
						url = "http://localhost:8081"
				),
				
		}
)
public class OpenAPIConfig {

}
