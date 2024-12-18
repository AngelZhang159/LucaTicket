package com.lucaticket.compraservice.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.compraservice.error.exception.CuentaNoRegistradaException;
import com.lucaticket.compraservice.error.exception.DatosCompraInvalidosException;
import com.lucaticket.compraservice.error.exception.DatosInvalidosException;
import com.lucaticket.compraservice.error.exception.EventoNotFoundException;
import com.lucaticket.compraservice.model.ValidUser;
import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;
import com.lucaticket.compraservice.model.dto.DetailedEventResponse;
import com.lucaticket.compraservice.model.dto.FallbackErrorResponse;
import com.lucaticket.compraservice.model.dto.TicketRequest;
import com.lucaticket.compraservice.model.dto.TicketResponse;
import com.lucaticket.compraservice.model.dto.ValidarCompraResponse;
import com.lucaticket.compraservice.model.dto.ValidarUserResponse;
import com.lucaticket.compraservice.service.CompraService;
import com.lucaticket.feignclients.BancoFeignClient;
import com.lucaticket.feignclients.EventFeignClient;
import com.lucaticket.feignclients.TicketFeignClient;
import com.lucaticket.feignclients.UserFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@EnableFeignClients
@Slf4j
public class CompraServiceImpl implements CompraService {

	private final EventFeignClient eventFeign;
	private final BancoFeignClient bancoFeign;
	private final TicketFeignClient ticketFeign;
	private final UserFeignClient userFeign;

	@Override
	public ResponseEntity<CompraResponse> buy(CompraRequest compraRequest) {
		
		log.info("Nueva compra: " + compraRequest.toString());
		comprobarCuenta(compraRequest.getEmail());
		ResponseEntity<DetailedEventResponse> detailedEventResponse = eventExists(compraRequest);

		ResponseEntity<ValidarUserResponse> validarUserResponse = validateUser();

		validatePurchase(compraRequest, detailedEventResponse, validarUserResponse);

		saveTicket(compraRequest);

		return ResponseEntity.ok(new CompraResponse(compraRequest.getNombreTitular(), compraRequest.getEmail(),
				compraRequest.getIdEvento(), detailedEventResponse.getBody().getName(), compraRequest.getCantidad(),
				detailedEventResponse.getBody().getEventDate()));
	}

	private void saveTicket(CompraRequest compraRequest) {
		log.info("Guardando ticket de la compra: " + compraRequest.toString());
		ResponseEntity<TicketResponse> ticketResponse = ticketFeign
				.save(new TicketRequest(compraRequest.getEmail(), compraRequest.getIdEvento()));

		if (ticketResponse.getStatusCode() != HttpStatus.OK) {
			log.info("Error al guardar el ticket en la base de datos: " + ticketResponse.getBody());
		}
		log.info("Ticket guardado con éxito");
	}
	
	private void comprobarCuenta(String email) {
		log.info("Comprobando si existe una cuenta registrada con el email" + email);
		if(userFeign.getUser(email).getStatusCode() != HttpStatus.ACCEPTED) {
			log.info("No existe cuenta con el email: " + email);
			throw new CuentaNoRegistradaException("No existe cuenta con el email: [" + email 
					+ "], por favor, regístrese para utilizar el servicio de compra");
		}
	}

	private void validatePurchase(CompraRequest compraRequest,
			ResponseEntity<DetailedEventResponse> detailedEventResponse,
			ResponseEntity<ValidarUserResponse> validarUserResponse) {

		String token = validarUserResponse.getBody().getToken();
		CompraRequest compra = rellenarDatos(compraRequest, detailedEventResponse.getBody());

		log.info("Validando la compra, token: " + token + ", compra: " + compra.toString());

		ResponseEntity<ValidarCompraResponse> validarCompraResponse = bancoFeign.validarCompra(token, compra);

		if (validarCompraResponse.getStatusCode() != HttpStatus.OK) {
			log.error("Error al validar la compra: " + validarCompraResponse.toString());
			throw new DatosCompraInvalidosException(validarCompraResponse.getBody().getError().substring(0, 9));
		}
		log.info("Compra validada con éxito");
	}

	private ResponseEntity<ValidarUserResponse> validateUser() {
		log.info("Validando usuario con el banco, user:" + ValidUser.name + ", password" + ValidUser.password);
		ResponseEntity<ValidarUserResponse> validarUserResponse = bancoFeign.validarUser(ValidUser.name,
				ValidUser.password);
		if (validarUserResponse.getStatusCode() != HttpStatus.OK) {
			log.error("El usuario no es válido");
			throw new DatosInvalidosException("User o password incorrecta");
		}
		log.info("Usuario validado con éxito");
		return validarUserResponse;
	}

	private ResponseEntity<DetailedEventResponse> eventExists(CompraRequest compraRequest) {
		log.info("Comprobando evento con ID: " + compraRequest.getIdEvento());
		ResponseEntity<DetailedEventResponse> detailedEventResponse = eventFeign.getDetail(compraRequest.getIdEvento());
		if (detailedEventResponse.getStatusCode() != HttpStatus.OK) {
			log.error("El evento con ID: " + compraRequest.getIdEvento() + " no existe");
			throw new EventoNotFoundException("El evento " + compraRequest.getIdEvento() + " no existe");
		}
		log.info("Evento con ID: " + compraRequest.getIdEvento() + " encontrado: " + detailedEventResponse.toString());
		return detailedEventResponse;
	}

	private CompraRequest rellenarDatos(CompraRequest compraRequest, DetailedEventResponse detailedEventResponse) {
		Random aleatorio = new Random();

		compraRequest.setEmisor("GRUPO 2 LUCATICKET");
		compraRequest.setConcepto("EVENTO: [" + detailedEventResponse.getName() + "] ");
		compraRequest.setCantidad(
				aleatorio.nextDouble(detailedEventResponse.getMaxPrice() - detailedEventResponse.getMinPrice() + 1)
						+ detailedEventResponse.getMinPrice());

		return compraRequest;
	}
}
