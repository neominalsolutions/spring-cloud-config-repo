package com.mertalptekin.orderservicedemo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String product;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String status;

    // getter-setter’lar
}