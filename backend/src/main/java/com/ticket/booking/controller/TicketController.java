package com.ticket.booking.controller;

import com.ticket.booking.model.TicketConfiguration;
import com.ticket.booking.service.TicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {

    private final TicketManager ticketManager;
    private final TicketConfiguration ticketConfiguration;

    public TicketController(TicketManager ticketManager, TicketConfiguration ticketConfiguration) {
        this.ticketManager = ticketManager;
        this.ticketConfiguration = ticketConfiguration;
    }


    @PostMapping("/configure")
    public ResponseEntity<String> configure(@RequestBody TicketConfiguration config) {
        ticketConfiguration.setMaxTickets(config.getMaxTickets());
        ticketConfiguration.setTotalTickets(config.getTotalTickets());
        ticketConfiguration.setTicketReleaseRate(config.getTicketReleaseRate());
        ticketConfiguration.setCustomerRetrievalRate(config.getCustomerRetrievalRate());
        return ResponseEntity.ok("Configuration updated successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("isRunning", ticketManager.isRunning());
        return ResponseEntity.ok(status);
    }


    @PostMapping("/start")
    public void start() {
        ticketManager.start();
    }

    @PostMapping("/stop")
    public void stop() {
        ticketManager.stop();
    }
}
