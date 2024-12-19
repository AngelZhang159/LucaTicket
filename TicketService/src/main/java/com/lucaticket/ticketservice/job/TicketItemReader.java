package com.lucaticket.ticketservice.job;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import com.lucaticket.ticketservice.model.Ticket;
import com.lucaticket.ticketservice.repository.TicketRepository;

@Component
public class TicketItemReader implements ItemReader<List<Ticket>> {

    private final TicketRepository ticketRepository;
    private final List<Long> eventIds;
    private int currentIndex = 0;

    public TicketItemReader(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.eventIds = ticketRepository.findDistinctIdEvent(); // IDs únicos
    }

    @Override
    public List<Ticket> read() {
        if (currentIndex < eventIds.size()) {
            Long currentEventId = eventIds.get(currentIndex++);
            return ticketRepository.findByIdEvent(currentEventId); // Tickets por ID de evento
        }
        return null; // Fin del flujo
    }
}
