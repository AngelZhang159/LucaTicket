package com.lucaticket.ticketservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.lucaticket.ticketservice.controller.TicketController;
import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;
import com.lucaticket.ticketservice.service.TicketService;

@SpringBootTest
class TicketServiceApplicationTests {
	
	@Autowired
    private TicketController ticketController;
	
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketService ticketService;

	@Test
	void contextLoads() {
		

	}
	
	/**
	 * @author Alberto de la Blanca
	 * comprobar que la cantidad de elementos de la lista devuelta sea la misma que los elementos presentes en la base de datos. 
	 * 
	 **/
	
	@Test
	public void test_List_Ticket_Matches_Database_Size() {
        // Configurar el comportamiento del mock
        when(ticketRepository.findAll()).thenReturn(List.of(
            new Ticket(1L, "user1@example.com", 1L),
            new Ticket(2L, "user2@example.com", 2L)
        ));

        // Llamar al controlador para listar los tickets
        ResponseEntity<List<TicketResponse>> response = ticketController.listTickets();

        // Verificar que el tamaño de la lista devuelta coincide con los elementos en el mock
        assertEquals(2, response.getBody().size(), "El número de tickets devueltos debe coincidir con el mock.");
    }
	
	/**
	 * @author Alberto de la Blanca
	 * al listar y no haya tickets, devuelva un 204.
	 * 
	 **/
	@Test
	public void test_ListTicket_When_No_Ticket_Exist() {
		
        // Configurar el mock para devolver una lista vacía
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        // Llamar al controlador
        ResponseEntity<List<TicketResponse>> response = ticketController.listTickets();

        // Verificar que la respuesta tiene el código de estado 204
        assertEquals(204, response.getStatusCode(), "Cuando no hay tickets, debería devolver 204 No Content.");

        // Verificar que el cuerpo de la respuesta está vacío
        assertEquals(null, response.getBody(), "El cuerpo de la respuesta debe ser null cuando no hay tickets.");
  
	}
	
	/**
	 * @author Alberto de la Blanca
	 * al listar por email y no haya tickets, devuelva un 204.
	 * 
	 **/
	@Test
    public void test_GetTicketsByMail_When_No_Tickets_Exist() {
        // Configurar el mock para devolver una lista vacía
        when(ticketRepository.findByEmail(Mockito.anyString())).thenReturn(Collections.emptyList());

        // Llamar al método con un correo para el que no hay tickets
        ResponseEntity<List<DetailedTicketResponse>> response = ticketController.getTickets("user@example.com");

        // Verificar que la respuesta tiene el código de estado 204
        assertEquals(204, response.getStatusCode(), "Cuando no hay tickets, debería devolver 204 No Content.");

        // Verificar que el cuerpo está vacío
        assertEquals(null, response.getBody(), "El cuerpo de la respuesta debe ser null cuando no hay tickets.");

	}
	
	/**
	 * @author Alberto de la Blanca
     * Verifica que el número de tickets listados coincide con el número de tickets
     * en la base de datos para un correo dado.
     */
	
	@Test
	public void test_GetTicketsByMail_Matches_Satabase_Size() {
        // Configurar el mock para devolver tickets específicos por email
        when(ticketRepository.findByEmail("user1@example.com")).thenReturn(List.of(
            new Ticket(1L, "user1@example.com", 1L),
            new Ticket(3L, "user1@example.com", 3L)
        ));

        // Llamar al método con un correo específico
        ResponseEntity<List<DetailedTicketResponse>> response = ticketController.getTickets("user1@example.com");

        // Verificar que el tamaño de la lista coincide con los tickets asociados al correo
        assertEquals(2, response.getBody().size(), "El número de tickets devueltos debe coincidir con el mock para el correo proporcionado.");
    
	}   
}


