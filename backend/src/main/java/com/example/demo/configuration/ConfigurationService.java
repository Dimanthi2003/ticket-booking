package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository repository;

    public Configuration saveConfiguration(Configuration config) {
        return repository.save(config);
    }

    public long getTicketCount() {
        // Simulating a live ticket count; replace with real logic if needed.
        return repository.findAll().stream().mapToInt(Configuration::getTotalTickets).sum();
    }
}