package com.example.swp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "warranties")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Warranty extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_warranty")
    private int timeWarranty;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "warranty_detail")
    private String warrantyDetail;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}

