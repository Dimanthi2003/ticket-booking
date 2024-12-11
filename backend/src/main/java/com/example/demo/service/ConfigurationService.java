package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
<<<<<<< Updated upstream
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository repository;

    public Configuration saveConfiguration(Configuration config) {
        return repository.save(config);
    }

    public long getTicketCount() {
        // Simulating a live ticket count; replace with real logic if needed.
        return repository.findAll().stream().mapToInt(Configuration::getTotalTickets).sum();
=======
public class TicketManager {

    private final SimpMessagingTemplate messagingTemplate;
    private final TicketConfiguration ticketConfiguration;

    private int availableTickets = 0;
    private boolean isRunning = false;

    public TicketManager(SimpMessagingTemplate messagingTemplate, TicketConfiguration ticketConfiguration) {
        this.messagingTemplate = messagingTemplate;
        this.ticketConfiguration = ticketConfiguration;
    }

    @Scheduled(fixedRateString = "#{@ticketConfiguration.ticketReleaseRate}")
    public void releaseTickets() {
        if (isRunning && availableTickets < ticketConfiguration.getTotalTickets()) {
            availableTickets++;
            messagingTemplate.convertAndSend("/topic/tickets", "Ticket released. Total available: " + availableTickets);
        }
    }

    @Scheduled(fixedRateString = "#{@ticketConfiguration.customerRetrievalRate}")
    public void retrieveTickets() {
        if (isRunning && availableTickets > 0) {
            availableTickets--;
            messagingTemplate.convertAndSend("/topic/tickets", "Customer retrieved a ticket. Remaining: " + availableTickets);
        }
    }

    public void start() {
        isRunning = true;
        messagingTemplate.convertAndSend("/topic/tickets", "Ticket management started.");
    }

    public void stop() {
        isRunning = false;
        messagingTemplate.convertAndSend("/topic/tickets", "Ticket management stopped.");
    }

    public boolean isRunning() {
        return isRunning;
>>>>>>> Stashed changes
    }
}
