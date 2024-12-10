package com.lucaticket.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.event.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.java.Log;


@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private final EventService eventService;

    /*
     * @Olivord
     * Guarda el evento en la base de datos
     */
    @PostMapping
    public ResponseEntity<EventoResponse> saveEvent(@RequestBody @Valid EventoRequest) {
        Log.info("Guardando evento");
        Event newEvent = eventService.saveEvent(eventoRequest);
        return ResponseEntity.ok(newEvent);
    }
    
}
