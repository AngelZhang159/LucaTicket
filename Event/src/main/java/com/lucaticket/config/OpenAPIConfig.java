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
				description = "Documentación del projecto 2 [Luca Steam Spring]",
				title = "Luca Steam Spring",
				version = "1.0"
				),
		servers = {
				@Server(
						description = "Local",
						url = "http://localhost:8080"
				),
				@Server(
						description = "Producción",
						url = "http://3.90.213.131:8080"
				)
				
		}
)
public class OpenAPIConfig {

}
