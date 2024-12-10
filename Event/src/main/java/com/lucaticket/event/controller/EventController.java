package com.lucaticket.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.event.model.dto.EventRequest;
import com.lucaticket.event.model.dto.EventResponse;
import com.lucaticket.event.service.EventService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@Slf4j
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    /*
     * @Olivord
     * Guarda el evento en la base de datos
     */
    @PostMapping
    public ResponseEntity<EventResponse> saveEvent(@RequestBody @Valid EventRequest eventoRequest) {
        log.info("EventController.saveEvent");
        return eventService.saveEvent(eventoRequest);
    }
    
}
