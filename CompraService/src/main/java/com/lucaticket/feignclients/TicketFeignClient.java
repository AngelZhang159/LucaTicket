package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "ticket")
public interface TicketFeignClient {
	
//	@PostMapping
//	ResponseEntity<TicketResponse> save(@RequestBody TicketRequest ticketRequest );
//
//	@GetMapping
//	ResponseEntity<List<TicketResponse>> listTickets();
}
