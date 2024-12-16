package com.lucaticket.compraservice.service;

import org.springframework.http.ResponseEntity;

import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;

public interface CompraService {

	ResponseEntity<CompraResponse> buy(CompraRequest compraRequest);
	
}
