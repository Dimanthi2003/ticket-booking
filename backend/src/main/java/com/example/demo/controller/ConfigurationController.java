package com.example.demo.controller;

import com.example.demo.model.Configuration;
import com.example.demo.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ConfigurationController {

    @Autowired
    private ConfigurationService service;

    @PostMapping("/configuration")
    public ResponseEntity<?> saveConfiguration(@RequestBody Configuration config) {
        if (config.getMaxTickets() <= config.getTotalTickets()) {
            return ResponseEntity.badRequest().body("Max tickets must be greater than total tickets.");
        }
        service.saveConfiguration(config);
        return ResponseEntity.ok("Configuration saved successfully.");
    }

    @GetMapping("/tickets/count")
    public ResponseEntity<?> getTicketCount() {
        long ticketCount = service.getTicketCount();
        return ResponseEntity.ok().body(new TicketCountResponse(ticketCount));
    }

    static class TicketCountResponse {
        private long count;

        public TicketCountResponse(long count) {
            this.count = count;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}
