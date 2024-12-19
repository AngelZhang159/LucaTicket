package com.lucaticket.ticketservice.job;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lucaticket.ticketservice.model.Historico;
import com.lucaticket.ticketservice.model.Ticket;

@Component
public class TicketItemProcessor implements ItemProcessor<List<Ticket>, Historico> {

    @Override
    public Historico process(List<Ticket> tickets) {
        Long idEvento = tickets.get(0).getIdEvent();
        double precioMax = tickets.stream().mapToDouble(Ticket::getPrice).max().orElse(0.0);
        double precioMin = tickets.stream().mapToDouble(Ticket::getPrice).min().orElse(0.0);
        double media = tickets.stream().mapToDouble(Ticket::getPrice).average().orElse(0.0);
        int numeroVentas = tickets.size();

        Historico historico = new Historico();
        historico.setIdEvento(idEvento);
        historico.setMedia(media);
        historico.setPrecioMax(precioMax);
        historico.setPrecioMin(precioMin);
        historico.setNumeroVentas(numeroVentas);
        historico.setTimestamp(LocalDateTime.now());

        return historico; // Devuelve el histórico para guardar
    }
}

