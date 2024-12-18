package com.lucaticket.compraservice.service;

import org.springframework.http.ResponseEntity;

import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;

public interface CompraService {

	/**
	 * @author Raul, Angel, Yuji, Alberto
	 * @param compraRequest
	 * @return
	 */
	ResponseEntity<CompraResponse> buy(CompraRequest compraRequest);
	
}
