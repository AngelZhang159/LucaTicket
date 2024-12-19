package com.lucaticket.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Angel
 */
@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Grupo 2" 
						),
				title = "LucaTicket Spring",
				description = "Documentación del projecto 3 [LucaTicket]",
				version = "1.0"
				),
		servers = {
				@Server(
						description = "Local",
						url = "http://localhost:8080"
				),
				
		}
)
public class OpenAPIConfig {

}
