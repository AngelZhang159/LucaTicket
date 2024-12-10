package com.lucaticket.event;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.impl.EventServiceImpl;

@SpringBootTest
class EventApplicationTests {
//	<-- Atributos -->
	
	@InjectMocks
	private EventServiceImpl eventService;
	
	@Mock
	private EventRepository eventRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Autowired
	private DataSource dataSource;

	// @Olivord
	@Test
	void configureDatabaseConnection_shouldSucceed() {
		// Verifica que el DataSource no sea nulo
		assertNotNull(dataSource, "La conexión a la base de datos debería estar configurada correctamente.");

		try (Connection connection = dataSource.getConnection()) {
			// Verifica que la conexión se establece correctamente
			assertNotNull(connection, "La conexión con la base de datos debería haberse establecido.");

			// Extrae metadatos para verificar que la conexión apunta a la base de datos
			// correcta
			DatabaseMetaData metaData = connection.getMetaData();
			String databaseUrl = metaData.getURL();
			String databaseUser = metaData.getUserName();

			// Valida que el URL y usuario coincidan con la configuración proporcionada
			assertEquals(
					"jdbc:mysql://junction.proxy.rlwy.net:25537/lucatickect?useSSL=false&serverTimezone=UTC",
					databaseUrl,
					"El URL de la base de datos debería coincidir con la configuración proporcionada.");
			assertEquals(
					"root",
					databaseUser,
					"El usuario de la base de datos debería coincidir con la configuración proporcionada.");
		} catch (Exception e) {
			throw new AssertionError("No se pudo conectar con la base de datos.", e);
		}
	}
	
	@Test
	void saveEvent_should_return_saved_event() {
//	<-- Atributos -->
		String str = "2025-01-01";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		
		EventRequest peticionEvento = new EventRequest("Galacticon", "Una emocionante aventura", dateTime, 10.00, 20.00, "Madrid", "Wizink", Genre.METAL);
		Event evento = peticionEvento.toEntity();

//	<-- MOCKING ->>
		when(eventRepository.save(any(Event.class))).thenReturn(evento);
		
		ResponseEntity<EventResponse> result = eventService.saveEvent(peticionEvento);
		
//	<-- Aserciones -->
		assertNotNull(result);
		assertEquals("Galacticon", result.getBody().getName());
	}
	//@Alberto
	@Test
	void saveEvent_shouldThrowErrorWhenRequestIsInvalid() {
		//Crear DTO con datos inválidos
		EventoDTO invalidEvent = new EventDTO();
		invalidEvent.setName(""); //nombre vacio
		invalidEvent.setEventDate(null); //fecha nula
		invalidEvent.setMinPrice(-5.0);//precio minimo negativo
		
		//verificar que se lanza excepcion
		assertThrows(
	            InvalidDataException.class, 
	            () -> eventService.saveEvent(invalidEvent),
	            "Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos."
	        );
	}
	


}
