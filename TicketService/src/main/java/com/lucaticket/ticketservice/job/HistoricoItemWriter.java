package com.lucaticket.ticketservice.job;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.lucaticket.ticketservice.model.Historico;
import com.lucaticket.ticketservice.repository.HistoricoRepository;

@Component
public class HistoricoItemWriter implements ItemWriter<Historico> {

    private final HistoricoRepository historicoRepository;

    public HistoricoItemWriter(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @Override
    public void write(Chunk<? extends Historico> items) {
        historicoRepository.saveAll(items);
    }
}