package com.example.demo.ticket;

import com.example.demo.producerconsumer.ProducerConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final ProducerConsumerService producerConsumerService;
    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

    // Constructor for manual injection if Lombok is not working
    @Autowired
    public TicketController(ProducerConsumerService producerConsumerService,
                            TicketService ticketService,
                            SimpMessagingTemplate messagingTemplate) {
        this.producerConsumerService = producerConsumerService;
        this.ticketService = ticketService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/produce")
    public void produceTickets(@RequestParam int count, @RequestParam String eventName) {
        producerConsumerService.produceTickets(count, eventName);
    }

    @PostMapping("/consume")
    public void consumeTickets(@RequestParam int count) {
        producerConsumerService.consumeTickets(count);
    }

    @GetMapping("/available")
    public long getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }

    @GetMapping("/stream")
    public void streamTicketStatus() {
        long availableTickets = ticketService.getAvailableTickets();
        messagingTemplate.convertAndSend("/topic/tickets", availableTickets);
    }
}
