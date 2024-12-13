package com.lucaticket.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucaticket.ticketservice.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
}
