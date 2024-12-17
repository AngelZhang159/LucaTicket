package com.lucaticket.ticketservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.lucaticket.ticketservice.controller.TicketController;
import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.repository.TicketRepository;

@SpringBootTest
class TicketServiceApplicationTests {
	
    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketRepository ticketRepository;

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
		// Limpiar la base de datos antes de iniciar la prueba
        ticketRepository.deleteAll();
		
        // Crear y guardar tickets de prueba
        Ticket ticket1 = new Ticket(null, "user1@example.com", 1L);
        Ticket ticket2 = new Ticket(null, "user2@example.com", 2L);
        ticketRepository.saveAll(Arrays.asList(ticket1, ticket2));

        // Llamar al controlador para listar los tickets
        ResponseEntity<List<TicketResponse>> response = ticketController.listTickets();

        // Verificar que el tamaño de la lista devuelta coincide con los elementos en la base de datos
        assertEquals(2, response.getBody().size(), "El número de tickets devueltos debe coincidir con el de la base de datos.");
    }
	
	/**
	 * @author Alberto de la Blanca
	 * al listar y no haya tickets, devuelva un 204.
	 * 
	 **/
	@Test
	public void test_ListTicket_When_No_Ticket_Exist() {
		
		ticketRepository.deleteAll();
		ResponseEntity<List<TicketResponse>> response = ticketController.listTickets();
		
		// Verificar que la respuesta tiene el código de estado 204 (No Content)
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
		//Limpiar bbdd
		ticketRepository.deleteAll();
		
		// Llamar al método con un correo para el que no hay tickets
        ResponseEntity<List<DetailedTicketResponse>> response = ticketController.getTickets("user@example.com");
        
     // Verificar que la respuesta tiene el código de estado 204 (No Content)
        assertEquals(204, response.getStatusCode(), "Cuando no hay tickets, debería devolver 204 No Content.");
        
        //Verificar que el cuerpo esta vacio
        assertEquals(null, response.getBody(), "El cuertpo de la respuesta debe ser null cuando no hay tickets");

	}
}


