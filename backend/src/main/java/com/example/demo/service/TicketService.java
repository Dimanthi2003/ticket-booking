package com.example.demo.service;

import com.example.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public long getAvailableTickets() {
        return ticketRepository.countBySold(false);
    }
}
