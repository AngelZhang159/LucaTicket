package com.lucaticket.ticketservice.job;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.repository.TicketRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TicketItemReader implements ItemReader<List<Ticket>> {

    private final TicketRepository ticketRepository;
    Iterator<Long> list;
    private int currentIndex = 0;

    public TicketItemReader(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        list = ticketRepository.findDistinctIdEvent().listIterator(); // IDs únicos
    }
    
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        // Initialize the list before the step starts
        this.list = ticketRepository.findDistinctIdEvent().listIterator(); // IDs únicos
        log.info("Initialized TicketItemReader with distinct event IDs.");
    }

    @Override
    public List<Ticket> read() {
    	if(list == null) {
    		log.error("-------- soy null");
    	}
        if (list != null && list.hasNext()) {
            Long currentEventId = list.next();
            return ticketRepository.findByIdEvent(currentEventId); // Tickets por ID de evento
        }
        return null; // Fin del flujo
    }
}
