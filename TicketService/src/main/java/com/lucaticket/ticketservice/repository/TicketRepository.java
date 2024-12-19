package com.lucaticket.ticketservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lucaticket.ticketservice.model.Ticket;

/**
 * Repositorio para la entidad Ticket.
 * 
 * Proporciona operaciones CRUD y consultas personalizadas para la gestión de tickets
 * en la base de datos.
 * 
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	boolean existsByEmailAndIdEvent(String email, Long idEvent);

	List<Ticket> findByEmail(String email);

	@Query("SELECT DISTINCT t.idEvent FROM tickets t")
    List<Long> findDistinctIdEvent();

    // Obtener tickets por ID de evento
    List<Ticket> findByIdEvent(Long idEvento);
}
