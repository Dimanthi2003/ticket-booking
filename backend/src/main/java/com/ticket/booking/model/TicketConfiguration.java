package com.ticket.booking.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ticket")
public class TicketConfiguration {
    private int maxTickets;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
}

