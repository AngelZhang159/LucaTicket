package com.lucaticket.compraservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;
import com.lucaticket.compraservice.service.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Angel
 */
@Slf4j
@RestController
public class CompraController {

	@Autowired
	CompraService compraService;
	
	@PostMapping("/ticket/{idEvento}") 
	@Operation(description = "Realiza la compra de un ticket para un evento a partir del ID de evento y una peticion de compra")
	ResponseEntity<CompraResponse> buy(@PathVariable Long idEvento, @RequestBody @Valid CompraRequest compraRequest) {
		compraRequest.setIdEvento(idEvento);
		return compraService.buy(compraRequest);
	}
}
