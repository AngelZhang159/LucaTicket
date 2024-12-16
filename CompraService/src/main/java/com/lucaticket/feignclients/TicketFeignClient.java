package com.lucaticket.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "ticket")
public interface TicketFeignClient {
	
//	@PostMapping
//	ResponseEntity<TicketResponse> save(@RequestBody TicketRequest ticketRequest );
//
//	@GetMapping
//	ResponseEntity<List<TicketResponse>> listTickets();
}
