package com.lucaticket.compraservice.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.compraservice.model.ValidUser;
import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;
import com.lucaticket.compraservice.model.dto.DetailedEventResponse;
import com.lucaticket.compraservice.model.dto.TicketRequest;
import com.lucaticket.compraservice.model.dto.ValidarCompraResponse;
import com.lucaticket.compraservice.model.dto.ValidarUserResponse;
import com.lucaticket.compraservice.service.CompraService;
import com.lucaticket.feignclients.BancoFeignClient;
import com.lucaticket.feignclients.EventFeignClient;
import com.lucaticket.feignclients.TicketFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableFeignClients
public class CompraServiceImpl implements CompraService {

	@Autowired
	private final EventFeignClient eventFeign;
	@Autowired
	private final BancoFeignClient bancoFeign;
	@Autowired
	private final TicketFeignClient ticketFeign;

	@Override
	public ResponseEntity<CompraResponse> buy(CompraRequest compraRequest) {

		ResponseEntity<DetailedEventResponse> detailedEventResponse = eventExists(compraRequest);

		ResponseEntity<ValidarUserResponse> validarUserResponse = validateUser();

		validatePurchase(compraRequest, detailedEventResponse, validarUserResponse);
		
		ticketFeign.save(new TicketRequest(compraRequest.getEmail(), compraRequest.getIdEvento()));

		return ResponseEntity.ok(new CompraResponse(compraRequest.getNombreTitular(), compraRequest.getEmail(),
				compraRequest.getIdEvento(), detailedEventResponse.getBody().getName(), compraRequest.getCantidad(), LocalDateTime.now()));
	}

	private void validatePurchase(CompraRequest compraRequest,
			ResponseEntity<DetailedEventResponse> detailedEventResponse,
			ResponseEntity<ValidarUserResponse> validarUserResponse) {
		ResponseEntity<ValidarCompraResponse> validarCompraResponse = bancoFeign
				.validarCompra(validarUserResponse.getBody().getToken(), rellenarDatos(compraRequest, detailedEventResponse.getBody()));
		
		if (validarCompraResponse.getStatusCode() != HttpStatus.OK) {
//			throw new DatosCompraInvalidosException("Los datos de la compra son erroneos");
		}
		
	}

	private ResponseEntity<ValidarUserResponse> validateUser() {
		ResponseEntity<ValidarUserResponse> validarUserResponse = bancoFeign.validarUser(ValidUser.name,
				ValidUser.password);
		if (validarUserResponse.getStatusCode() != HttpStatus.OK) {
//			throw new DatosInvalidosExcetpion("User o password incorrecta");
		}
		return validarUserResponse;
	}

	private ResponseEntity<DetailedEventResponse> eventExists(CompraRequest compraRequest) {
		ResponseEntity<DetailedEventResponse> detailedEventResponse = eventFeign.getDetail(compraRequest.getIdEvento());
		if (detailedEventResponse.getStatusCode() != HttpStatus.OK) {
//			throw new EventoNotFoundException("El evento " + compraRequest.getIdEvento() + " no existe");
		}
		return detailedEventResponse;
	}

	private CompraRequest rellenarDatos(CompraRequest compraRequest, DetailedEventResponse detailedEventResponse) {
		Random aleatorio = new Random();

		compraRequest.setEmisor("GRUPO 2 LUCATICKET");
		compraRequest.setConcepto("EVENTO: [" + detailedEventResponse.getName() + "] ");
		compraRequest.setCantidad(
				aleatorio.nextDouble(detailedEventResponse.getMaxPrice() - detailedEventResponse.getMinPrice() + 1) + detailedEventResponse.getMinPrice());

		return compraRequest;
	}

}
