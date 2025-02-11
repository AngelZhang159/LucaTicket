package com.lucaticket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Grupo 2" 
						),
				description = "Documentación del projecto 3 [LucaTicket]",
				title = "LucaTicket Spring",
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
