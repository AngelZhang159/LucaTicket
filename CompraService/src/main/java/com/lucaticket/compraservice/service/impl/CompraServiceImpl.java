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
import com.lucaticket.compraservice.feignclients.BancoFeignClient;
import com.lucaticket.compraservice.feignclients.EventFeignClient;
import com.lucaticket.compraservice.feignclients.TicketFeignClient;
import com.lucaticket.compraservice.feignclients.UserFeignClient;
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

	/**
     * Realiza el proceso completo de compra.
     * 
     * @param compraRequest Datos de la solicitud de compra.
     * @return Respuesta con los detalles de la compra realizada.
     */
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

	/**
     * Guarda un ticket en la base de datos.
     * 
     * @param compraRequest Datos de la solicitud de compra.
     */
	private void saveTicket(CompraRequest compraRequest) {
		log.info("Guardando ticket de la compra: " + compraRequest.toString());
		ResponseEntity<TicketResponse> ticketResponse = ticketFeign
				.save(new TicketRequest(compraRequest.getEmail(), compraRequest.getIdEvento(), compraRequest.getCantidad()));

		if (ticketResponse.getStatusCode() != HttpStatus.OK) {
			log.info("Error al guardar el ticket en la base de datos: " + ticketResponse.getBody());
		}
		log.info("Ticket guardado con éxito");
	}
	
	/**
     * Verifica si existe una cuenta registrada para el email proporcionado.
     * 
     * @param email Email del usuario.
     * @throws CuentaNoRegistradaException Si no existe una cuenta registrada con el email.
     */
	private void comprobarCuenta(String email) {
		log.info("Comprobando si existe una cuenta registrada con el email" + email);
		if(userFeign.getUser(email).getStatusCode() != HttpStatus.ACCEPTED) {
			log.info("No existe cuenta con el email: " + email);
			throw new CuentaNoRegistradaException("No existe cuenta con el email: [" + email 
					+ "], por favor, regístrese para utilizar el servicio de compra");
		}
	}

	/**
     * Valida los datos de una compra con el servicio del banco.
     * 
     * @param compraRequest Datos de la compra.
     * @param detailedEventResponse Detalles del evento.
     * @param validarUserResponse Validación del usuario.
     * @throws DatosCompraInvalidosException Si la validación falla.
     */
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

	 /**
     * Valida al usuario con el servicio del banco.
     * 
     * @return Respuesta de validación del usuario.
     * @throws DatosInvalidosException Si las credenciales del usuario son incorrectas.
     */
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

	/**
     * Comprueba si un evento existe en el sistema.
     * 
     * @param compraRequest Datos de la solicitud de compra.
     * @return Respuesta con los detalles del evento.
     * @throws EventoNotFoundException Si el evento no existe.
     */
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

	/**
     * Rellena los datos faltantes de una solicitud de compra.
     * 
     * @param compraRequest Datos de la solicitud de compra.
     * @param detailedEventResponse Detalles del evento.
     * @return Solicitud de compra con los datos completos.
     */
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
