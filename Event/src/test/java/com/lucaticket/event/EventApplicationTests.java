package com.lucaticket.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.lucaticket.event.model.Event;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
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
	
	@Test
	void saveEvent_should_return_saved_event() {
//	<-- Atributos -->
		String str = "2025-01-01";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		
		EventRequest peticionEvento = new EventRequest("Galacticon", "Una emocionante aventura", dateTime, 10.00, 20.00, "Madrid", "Wizink", Genre.Metal);
		Event evento = peticionEvento.toEntity();

//	<-- MOCKING ->>
		when(eventRepository.save(any(Event.class))).thenReturn(evento);
		
		ResponseEntity<EventResponse> result = eventService.saveEvent(peticionEvento);
		
//	<-- Aserciones -->
		assertNotNull(result);
		assertEquals("Galacticon", result.getBody().getName());
	}

}
