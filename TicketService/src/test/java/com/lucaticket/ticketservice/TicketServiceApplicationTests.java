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
	public void testListTicketMatchesDatabaseSize() {
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
}


