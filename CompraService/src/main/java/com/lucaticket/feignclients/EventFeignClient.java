package com.lucaticket.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lucaticket.event.model.dto.DetailedEventResponse;
import com.lucaticket.event.model.dto.EventDTO;
import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;

@FeignClient(name = "event")
public interface EventFeignClient {

	@PostMapping
	public ResponseEntity<EventResponse> saveEvent(@RequestBody EventRequest eventoRequest);

	@GetMapping("/listAll")
	public ResponseEntity<List<EventResponse>> getEvents();

	@GetMapping("/detail/{id}")
	public ResponseEntity<DetailedEventResponse> getDetail(@PathVariable long id);

	@GetMapping("/list/{name}")
	public ResponseEntity<List<EventResponse>> listByName(@PathVariable String name);

	@PutMapping("/update")
	public ResponseEntity<DetailedEventResponse> updateEvent(@RequestBody EventDTO event);

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<EventResponse> deleteEvent(@PathVariable long id);
}
