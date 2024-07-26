package com.example.swp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private double discount;

    @Column(name="payment_method")
    private int paymentMethod;

    @Column(name="order_status")
    private int orderStatus;

    @Column(name = "created_by")
    private String createdBy;
    private String type;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counters counter;

}
