package com.example.demo.producerconsumer;

import com.example.demo.ticket.Ticket;
import com.example.demo.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProducerConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerConsumerService.class);

    private final TicketRepository ticketRepository;
    private final BlockingQueue<Ticket> ticketQueue = new LinkedBlockingQueue<>(100);

    // Constructor for dependency injection
    public ProducerConsumerService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void produceTickets(int count, String eventName) {
        for (int i = 0; i < count; i++) {
            Ticket ticket = new Ticket();
            ticket.setEventName(eventName);
            ticket.setSold(false);
            try {
                ticketQueue.put(ticket);
                logger.info("Produced ticket: {}", ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Ticket production was interrupted", e);
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
                logger.info("Consumed ticket: {}", ticket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Ticket consumption was interrupted", e);
                throw new IllegalStateException("Ticket consumption was interrupted", e);
            }
        }
    }
}
