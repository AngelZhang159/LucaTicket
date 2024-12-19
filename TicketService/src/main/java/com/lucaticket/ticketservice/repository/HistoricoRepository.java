package com.lucaticket.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucaticket.ticketservice.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
}
