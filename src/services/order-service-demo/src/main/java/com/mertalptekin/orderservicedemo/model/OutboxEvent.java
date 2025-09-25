package com.mertalptekin.orderservicedemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Data
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_type")
    private String aggregateType;

    @Column(name = "aggregate_id")
    private String aggregateId;

    private String type;

    private String payload;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Boolean processed;

    // Getter ve Setter’lar
    // (createdAt için default değer constructor’da set edilebilir)
}