package com.lucaticket.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lucaticket.event.error.InvalidDataException;
import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.impl.EventServiceImpl;

@SpringBootTest
class EventApplicationTests {
	// <-- Atributos -->

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

			// Valida que el URL coincide con la configuración proporcionada
			assertEquals("jdbc:mysql://junction.proxy.rlwy.net:25537/lucatickect?useSSL=false&serverTimezone=UTC",
					databaseUrl, "El URL de la base de datos debería coincidir con la configuración proporcionada.");

			// Valida que el usuario contiene "root"
			assertNotNull(databaseUser, "El usuario de la base de datos no debería ser nulo.");
			assertTrue(databaseUser.contains("root"),
					"El usuario de la base de datos debería contener 'root'. Usuario obtenido: " + databaseUser);
		} catch (Exception e) {
			throw new AssertionError("No se pudo conectar con la base de datos.", e);
		}
	}

	@Test
	void saveEvent_should_return_saved_event() {
		// <-- Atributos -->
		LocalDateTime dateTime = LocalDateTime.of(2025, 5, 20, 18, 00);

		EventRequest peticionEvento = new EventRequest("Galacticon", "Una emocionante aventura", dateTime, 10.00, 20.00,
				"Madrid", "Wizink", Genre.METAL);
		Event evento = peticionEvento.toEntity();

		// <-- MOCKING ->>
		when(eventRepository.save(any(Event.class))).thenReturn(evento);

		ResponseEntity<EventResponse> result = eventService.saveEvent(peticionEvento);

		// <-- Aserciones -->
		assertNotNull(result);
		assertEquals("Galacticon", result.getBody().getName());
	}

	// @Alberto
	@Test
	void saveEvent_shouldThrowErrorWhenRequestIsInvalid() {
		// Crear DTO con datos inválidos
		EventRequest invalidEvent = new EventRequest();
		invalidEvent.setName(""); // nombre vacio
		invalidEvent.setEventDate(null); // fecha nula
		invalidEvent.setMinPrice(-5.0);// precio minimo negativo

		when(eventRepository.save(any(Event.class))).thenThrow(InvalidDataException.class);

		// verificar que se lanza excepcion
		assertThrows(InvalidDataException.class, () -> eventService.saveEvent(invalidEvent),
				"Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos.");
	}

	@Test
	void listEvents_shouldReturn204WhenNoEventsExist() {
		when(eventRepository.findAll()).thenReturn(new ArrayList<Event>());

		ResponseEntity<List<EventResponse>> response = eventService.getEvents();

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void listEvents_shouldReturn200WhenEventsExist() {
		List<Event> events = new ArrayList<Event>();

		events.add(new Event(1L, "pepe", "asdfasdasdf", LocalDateTime.of(2024, 10, 10, 10, 10), 15, 20, "jskf",
				"lkwlds", Genre.BLUES));

		events.add(new Event(2L, "qwert", "asdfsdf", LocalDateTime.of(2024, 10, 10, 10, 10), 15, 20, "gwere", "ergrg",
				Genre.ELECTRONIC));

		when(eventRepository.findAll()).thenReturn(events);

		ResponseEntity<List<EventResponse>> response = eventService.getEvents();

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * @author Raul tests that the returned list, whem no events exit, is not null
	 */
	@Test
	void should_not_return_null_when_list_is_empty() {
//	<-- Attributes -->
		List<Event> respuesta = new ArrayList<>();

//	<-- Mocking ->>
		when(eventRepository.findAll()).thenReturn(respuesta);

		ResponseEntity<List<EventResponse>> respuestaEntity = eventService.getEvents();

		assertNotNull(respuestaEntity);
	}

	@Test
	void should_return_correct_size_list_when_events_exist() {
//		<-- Attributes -->
		List<Event> respuesta = new ArrayList<>();
		respuesta.add(new Event());
		respuesta.add(new Event());
		respuesta.add(new Event());

//		<-- Mocking ->>
		when(eventRepository.findAll()).thenReturn(respuesta);

		ResponseEntity<List<EventResponse>> respuestaEntity = eventService.getEvents();

		assertEquals(3, respuestaEntity.getBody().size());
	}

	/**
	 * @author Raul testea que el objeto listado se devuelva correctamente y
	 *         devuelva 200
	 */
	@Test
	void should_return_the_correct_object_and_code_200_when_get_detailed_event() {
		Event evento = new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		eventRepository.save(evento);
		ResponseEntity<DetailedEventResponse> respuesta = eventService.getDetailedInfoEvent(1L);

		assertEquals("Metal Militia", respuesta.getBody().getName());
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}

	/**
	 * @author Raul testea que devuelva un 404 cuando intente buscar un evento que
	 *         no existe
	 */
	@Test
	void should_return_404_when_event_doesnt_exists_when_get_detailed_event() {
		ResponseEntity<DetailedEventResponse> respuesta = eventService.getDetailedInfoEvent(123123L);

		assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
	}
	
	@Test
	void when_returning_list_should_be_same_size() {
		Event evento = new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		Event evento1 = new Event(2, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		Event evento2 = new Event(3, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		
		List<Event> comprobador = new ArrayList<>();
		comprobador.add(evento);
		comprobador.add(evento1);
		comprobador.add(evento2);
		
		ResponseEntity<List<EventResponse>> respuesta = eventService.findByName("Metal Militia");
		
		assertEquals(3, respuesta.getBody().size());
	}
}
