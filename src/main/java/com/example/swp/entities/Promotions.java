package com.example.swp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Builder
@Table(name = "promotions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String description;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name = "fixed_discount_amount")
    private Long fixedDiscountAmount;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "is_used")
    private boolean isUsed;
}
