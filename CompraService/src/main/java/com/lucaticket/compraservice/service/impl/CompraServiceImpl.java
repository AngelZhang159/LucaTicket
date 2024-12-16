package com.lucaticket.compraservice.service.impl;

import java.time.LocalDate;
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
import com.lucaticket.compraservice.model.dto.ValidarCompraResponse;
import com.lucaticket.compraservice.model.dto.ValidarUserResponse;
import com.lucaticket.compraservice.model.dto.DetailedEventResponse;
import com.lucaticket.compraservice.service.CompraService;
import com.lucaticket.feignclients.BancoFeignClient;
import com.lucaticket.feignclients.EventFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableFeignClients
public class CompraServiceImpl implements CompraService {

	@Autowired
	private final EventFeignClient eventFeign;
	@Autowired
	private final BancoFeignClient bancoFeign;

	@Override
	public ResponseEntity<CompraResponse> buy(CompraRequest compraRequest) {
		ValidUser user = new ValidUser();

		DetailedEventResponse respuesta = eventFeign.getDetail(compraRequest.getIdEvento()).getBody();
//		if(respuesta.getName().isEmpty()) {
//			throw new EventoNotFoundException("El evento " + compraRequest.getIdEvento() + " no existe"); 
//		}

		ResponseEntity<ValidarUserResponse> respuestaValidarUser = bancoFeign.validarUser(user.getName(),
				user.getPassword());
		if (respuestaValidarUser.getStatusCode() != HttpStatus.OK) {
//			throw new DatosInvalidosExcetpion("User o password incorrecta");
		}
		user.setToken(respuestaValidarUser.getBody().getToken());

		ResponseEntity<ValidarCompraResponse> respuestaValidarCompra = bancoFeign
				.validarCompra(user.getToken() ,rellenarDatos(compraRequest, respuesta));
		if (respuestaValidarCompra.getStatusCode() != HttpStatus.OK) {
//			throw new DatosCompraInvalidosException("Los datos de la compra son erroneos");
		}

		return ResponseEntity.ok(new CompraResponse(compraRequest.getOwnerName(), compraRequest.getEmail(), compraRequest.getIdEvento(), respuesta.getName(), compraRequest.getCantidad(), LocalDateTime.now()));
	}

	private CompraRequest rellenarDatos(CompraRequest compraRequest, DetailedEventResponse respuesta) {
		Random aleatorio = new Random();

		compraRequest.setEmisor("GRUPO 2 LUCATICKET");
		compraRequest.setConcepto("EVENTO: [" + respuesta.getName() + "] ");
		compraRequest.setCantidad(
				aleatorio.nextDouble(respuesta.getMaxPrice() - respuesta.getMinPrice() + 1) + respuesta.getMinPrice());

		return compraRequest;
	}

}
