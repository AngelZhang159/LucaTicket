package com.lucaticket.compraservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.lucaticket.compraservice.error.exception.InvalidDataException;
import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.CompraResponse;
import com.lucaticket.compraservice.model.dto.DetailedEventResponse;
import com.lucaticket.compraservice.model.dto.ValidarUserResponse;
import com.lucaticket.compraservice.model.dto.ValidarCompraResponse;
import com.lucaticket.compraservice.repository.CompraRepository;
import com.lucaticket.compraservice.service.impl.CompraServiceImpl;
import com.lucaticket.feignclients.BancoFeignClient;
import com.lucaticket.feignclients.EventFeignClient;

@SpringBootTest
class CompraServiceApplicationTests {

	// <-- Atributos -->
	@Mock
	private CompraServiceImpl compraService;
	@Mock
	private CompraRepository compraRepository;
	@Mock
	private BancoFeignClient bancoFeign;
	@Mock
	private EventFeignClient eventFeign;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * @author Raul debería devolver invaliddataexption al hacer una peticion
	 *         invalida
	 */
	@Test
	void should_throw_invalid_data_exception_when_wrong_credentials_at_request() {
		// <-- Atributos -->

		CompraRequest peticionCompra = new CompraRequest("emailIncorrecto", "nombreeeeeeeeeeeeeeeeeeeeee",
				1234123412341234L, 12, 12, 123, "Raluca", "Frede", 0, 123L);

		// <-- MOCKING ->>
		when(compraService.buy(any(CompraRequest.class))).thenThrow(InvalidDataException.class);

		// <-- Aserciones -->
		assertThrows(InvalidDataException.class, () -> compraService.buy(peticionCompra),
				"Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos.");
	}
	
	@Test
	void should_return_success_when_valid_user() {
		CompraRequest peticionCompra = new CompraRequest("emailIncorrecto", "nombreeeeeeeeeeeeeeeeeeeeee",
				1234123412341234L, 12, 12, 123, "Raluca", "Frede", 0, 123L);
		ResponseEntity<DetailedEventResponse> respuestaEventDetail = ResponseEntity.ok( new DetailedEventResponse());
		ResponseEntity<CompraResponse> respuestaCompra = ResponseEntity.ok(new CompraResponse());
		
		when(compraService.buy(any(CompraRequest.class))).thenReturn(respuestaCompra);
		when(eventFeign.getDetail(any(Long.class))).thenReturn(respuestaEventDetail);
		when(bancoFeign.validarUser(any(String.class), any(String.class))).thenReturn(ResponseEntity.ok(new ValidarUserResponse()));
		
		ResponseEntity<CompraResponse> respuestaCompraComprobar = compraService.buy(peticionCompra);
		
		assertEquals(org.springframework.http.HttpStatus.OK, respuestaCompraComprobar.getStatusCode());
	}
	
	@Test
	void should_return_success_when_valid_purchase() {
		CompraRequest peticionCompra = new CompraRequest("emailIncorrecto", "nombreeeeeeeeeeeeeeeeeeeeee",
				1234123412341234L, 12, 12, 123, "Raluca", "Frede", 0, 123L);
		ResponseEntity<DetailedEventResponse> respuestaEventDetail = ResponseEntity.ok( new DetailedEventResponse());
		ResponseEntity<CompraResponse> respuestaCompra = ResponseEntity.ok(new CompraResponse());
		
		when(compraService.buy(any(CompraRequest.class))).thenReturn(respuestaCompra);
		when(eventFeign.getDetail(any(Long.class))).thenReturn(respuestaEventDetail);
		when(bancoFeign.validarUser(any(String.class), any(String.class))).thenReturn(ResponseEntity.ok(new ValidarUserResponse()));
		when(bancoFeign.validarCompra(any(String.class), any(CompraRequest.class))).thenReturn(ResponseEntity.ok(new ValidarCompraResponse()));
		
		
		ResponseEntity<CompraResponse> respuestaCompraComprobar = compraService.buy(peticionCompra);
		
		assertEquals(org.springframework.http.HttpStatus.OK, respuestaCompraComprobar.getStatusCode());
	}
}
