package com.lucaticket.ticketservice.job;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.lucaticket.ticketservice.model.Historico;
import com.lucaticket.ticketservice.repository.HistoricoRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class HistoricoItemWriter implements ItemWriter<Historico> {

    private final HistoricoRepository historicoRepository;

    public HistoricoItemWriter(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    
    @Override
    public void write(Chunk<? extends Historico> items) {
        historicoRepository.saveAll(items);
        log.info("------ AQUI ESTOY WRITER FUNCIONO");
    }
}