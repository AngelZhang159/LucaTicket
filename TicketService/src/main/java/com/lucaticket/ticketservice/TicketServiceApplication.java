package com.lucaticket.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class TicketServiceApplication {
	
	/**
     * The main method that serves as the entry point for the Ticket Service application.
     *
     * @param args command-line arguments (if any) passed during application startup.
     */

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}
}
