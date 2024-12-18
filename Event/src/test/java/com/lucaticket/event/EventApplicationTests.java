package com.lucaticket.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lucaticket.event.controller.EventController;
import com.lucaticket.event.error.EventNotFoundException;
import com.lucaticket.event.error.InvalidDataException;
import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventCreateDelete;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.model.enums.Genre;
import com.lucaticket.event.repository.EventRepository;
import com.lucaticket.event.service.impl.EventServiceImpl;

@SpringBootTest
class EventApplicationTests {
	// <-- Atributos -->

	@Autowired
	private EventController eventController;

	@Autowired
	private EventServiceImpl eventService;

	@MockBean
	private EventRepository eventRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment enviroment;

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
			assertEquals(enviroment.getProperty("spring.datasource.url"),
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("(dd/MM/yyyy HH:mm)");

		EventRequest peticionEvento = new EventRequest("Galacticon", "Una emocionante aventura",
				dateTime.format(formatter), 10.00, 20.00, "Madrid", "Wizink", Genre.METAL);
		Event evento = peticionEvento.toEntity();

		// <-- MOCKING ->>
		when(eventRepository.save(any(Event.class))).thenReturn(evento);

		ResponseEntity<EventCreateDelete> result = eventService.saveEvent(peticionEvento);

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
		invalidEvent.setMaxPrice(-50.0);// precio minimo negativo

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
		Optional<Event> evento = Optional.ofNullable(new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));

		when(eventRepository.findById(any(Long.class))).thenReturn(evento);

		ResponseEntity<DetailedEventResponse> respuesta = eventService.getDetailedInfoEvent(1L);

		assertEquals("Metal Militia", respuesta.getBody().getName());
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}

	/**
	 * @author Raul testea que devuelva un 404 cuando intente buscar un evento que
	 *         no existe
	 */
	@Test
	void should_throw_exception_when_event_doesnt_exists_when_get_detailed_event() {
		when(eventRepository.findByName(any(String.class))).thenThrow(EventNotFoundException.class);

		assertThrows(EventNotFoundException.class, () -> eventService.getDetailedInfoEvent(123123L),
				"Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos.");
	}

	/**
	 * @author Angel
	 * 
	 */
	@Test
	void should_return_200_whenEventWithNameExists() {
		Event evento = new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("(dd/MM/yyyy HH:mm)");

		EventRequest evento1 = new EventRequest("Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0).format(formatter), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		when(eventRepository.save(any(Event.class))).thenReturn(evento);

		ResponseEntity<EventCreateDelete> entity = eventService.saveEvent(evento1);

		assertEquals(HttpStatus.OK, entity.getStatusCode());

	}

	/**
	 * @author Raul testea que se devuelva la cantidad adecuada de elementos
	 */
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

		when(eventRepository.findByName(any(String.class))).thenReturn(comprobador);

		ResponseEntity<List<EventResponse>> respuesta = eventService.findByName("Metal Militia");

		assertEquals(3, respuesta.getBody().size());
	}

	/**
	 * @author Alberto de la Blanca testea que devuelve 404 cuando no encuentre un
	 *         evento con el nombre dado
	 */
	@Test
	void should_return_404_when_event_not_found() {
		String eventName = "EventoInexistente";
		when(eventRepository.findByName(eventName)).thenReturn(List.of());

		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> eventService.getDetailedInfoEventByName(eventName),
				"Deberia lanzarse InvalidException cuando no se encuentra el evento por nombre.");

		assertEquals("El evento con nombre 'EventoInexistente' no existe.", exception.getMessage(),
				"El mensaje de la excepción debería ser el esperado.");
	}

	/**
	 * @author Raul testea que el tamaño de la lista de los eventos es la misma
	 *         antes y despues del update
	 */
	@Test
	void event_list_size_should_remain_the_same_after_update() {
		List<Event> eventosBase = new ArrayList<>();
		Optional<Event> evento = Optional.ofNullable(new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));
		Event evento1 = new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);
		eventosBase.add(evento1);

		EventDTO eventoUpdateDto = new EventDTO(1, "Hit the Lights", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		Event eventoUpdate = new Event(1, "Hit the Lights", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		when(eventRepository.save(any(Event.class))).thenReturn(eventoUpdate);
		when(eventRepository.findById(any(Long.class))).thenReturn(evento);
		when(eventRepository.findAll()).thenReturn(eventosBase);

		ResponseEntity<Map<String, Object>> respuesta = eventService.updateEvent(eventoUpdateDto);
		ResponseEntity<List<EventResponse>> listaRespuesta = eventService.getEvents();

		assertEquals(1, listaRespuesta.getBody().size());
	}

	/**
	 * @author Raul testea que devuelve un 200 al actualizar correctamente
	 */
	@Test
	void should_return_200_on_update() {
		Optional<Event> evento = Optional.ofNullable(new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));
		EventDTO eventoUpdateDto = new EventDTO(1, "Hit the Lights", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		Event eventoUpdate = new Event(1, "Hit the Lights", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		when(eventRepository.save(any(Event.class))).thenReturn(eventoUpdate);
		when(eventRepository.findById(any(Long.class))).thenReturn(evento);

		ResponseEntity<Map<String, Object>> respuesta = eventService.updateEvent(eventoUpdateDto);

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}

	/**
	 * @author Raul testea que se tira la excepcion cuando los datos introducidos
	 *         son invalidos
	 */
	@Test
	void should_throw_invalid_data_exception_on_wrong_data_when_update() {
		Optional<Event> evento = Optional.ofNullable(new Event(1, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));
		EventDTO eventoUpdateDto = new EventDTO(1, "Hit the Lights", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL);

		when(eventRepository.findById(any(Long.class))).thenReturn(evento);
		when(eventRepository.save(any(Event.class))).thenThrow(InvalidDataException.class);

		assertThrows(InvalidDataException.class, () -> eventService.updateEvent(eventoUpdateDto),
				"Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos.");
	}

	/**
	 * @author Alberto de la Blanca testea que realmente se ha borrado el evento de
	 *         la base de datos
	 */

	@Test
	void test_delete_event() {
		// evento de prueba
		Optional<Event> event = Optional.ofNullable(new Event(122, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));

		when(eventRepository.findById(any(Long.class))).thenReturn(event);

		ResponseEntity<EventCreateDelete> respuesta = eventService.deleteEvent(event.get().getId());

		when(eventRepository.findById(any(Long.class))).thenReturn(null);

		Optional<Event> deletedEvent = eventRepository.findById(respuesta.getBody().getId());
		assertNull(deletedEvent);
	}

	/**
	 * @author Alberto de la Blanca testea que al borrar un evento, devolver un 200
	 *         si se ha borrado con exito
	 */
	@Test
	void testDeleteEventReturns200() {
		Optional<Event> event = Optional.ofNullable(new Event(122, "Metal Militia", "Tus grupos favoritos de metal",
				LocalDateTime.of(2025, 8, 12, 12, 0), 10.0, 20.0, "Madrid", "Wizing", Genre.METAL));

		when(eventRepository.findById(any(Long.class))).thenReturn(event);

		ResponseEntity<EventCreateDelete> respuesta = eventService.deleteEvent(event.get().getId());

		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
	}

}
