package com.ticket.booking.service;

import com.ticket.booking.model.TicketConfiguration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
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
    }
}
