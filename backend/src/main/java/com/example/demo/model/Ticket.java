package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String eventName;
    private boolean isSold;
}
