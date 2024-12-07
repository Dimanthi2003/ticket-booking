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

    private final TicketRepository ticketRepository;  // Injected through constructor
    private final BlockingQueue<Ticket> ticketQueue = new LinkedBlockingQueue<>(100);

    public void produceTickets(int count, String eventName) {
        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setEventName(eventName);
            ticket.setSold(false);  // Mark as unsold initially
            try {
                ticketQueue.put(ticket);  // Put the ticket into the queue
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void consumeTickets(int count) {
        for (int i = 0; i < count; i++) {
            try {
                Ticket ticket = ticketQueue.take();  // Take the ticket from the queue
                ticket.setSold(true);  // Mark as sold
                ticketRepository.save(ticket);  // Save to database
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public BlockingQueue<Ticket> getTicketQueue() {
        return ticketQueue;
    }
}
