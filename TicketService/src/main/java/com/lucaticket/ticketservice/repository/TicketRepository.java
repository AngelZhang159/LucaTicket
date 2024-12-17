package com.lucaticket.ticketservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucaticket.ticketservice.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	boolean existsByEmailAndIdEvent(String email, Long idEvent);

	List<Ticket> findByEmail(String email);
}
