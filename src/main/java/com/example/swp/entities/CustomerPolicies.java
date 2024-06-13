package com.example.swp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Table(name = "customer_policy")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPolicies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "discount_rate")
    private double discountRate;

    @Column(name = "fixed_discount_amount")
    private Long fixedDiscountAmount;

    @Column(name = "valid_from")
    private Date validFrom;

    @Column(name = "valid_to")
    private Date validTo;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "approval_required")
    private boolean approvalRequired;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "publishing_status")
    private String publishingStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;


}
