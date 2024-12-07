package com.example.demo.service;

import com.example.demo.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public long getAvailableTickets() {
        return ticketRepository.countByIsSold(false);  // Count unsold tickets
    }
}
