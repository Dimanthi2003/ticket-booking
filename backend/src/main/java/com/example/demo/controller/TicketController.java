package com.example.demo.controller;

import com.example.demo.service.ProducerConsumerService;
import com.example.demo.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    private final ProducerConsumerService producerConsumerService;
    private final TicketService ticketService;
    private final SimpMessagingTemplate messagingTemplate;

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
        messagingTemplate.convertAndSend("/topic/tickets", getAvailableTickets());
    }
}
