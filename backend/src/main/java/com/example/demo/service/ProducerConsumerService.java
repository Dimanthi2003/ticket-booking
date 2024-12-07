package com.example.demo.service;

import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class ProducerConsumerService {

    private final TicketRepository ticketRepository;
    private final BlockingQueue<Ticket> ticketQueue = new LinkedBlockingQueue<>(100);

    public void produceTickets(int count, String eventName) {
        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setEventName(eventName);
            ticket.setSold(false);
            try {
                ticketQueue.put(ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Ticket production was interrupted", e);
            }
        }
    }

    public void consumeTickets(int count) {
        for (int i = 0; i < count; i++) {
            try {
                Ticket ticket = ticketQueue.take();
                ticket.setSold(true);
                ticketRepository.save(ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Ticket consumption was interrupted", e);
            }
        }
    }
}
