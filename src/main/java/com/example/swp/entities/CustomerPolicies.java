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

    @JsonProperty(value = "discount_rate")
    private double discountRate;

    @JsonProperty(value = "fixed_discount_amount")
    private Long fixedDiscountAmount;

    @JsonProperty(value = "valid_from")
    private Date validFrom;

    @JsonProperty(value = "valid_to")
    private Date validTo;

    @JsonProperty(value = "created_by")
    private String createdBy;

    @JsonProperty(value = "approval_required")
    private boolean approvalRequired;

    @JsonProperty(value = "approved_by")
    private String approvedBy;

    @JsonProperty(value = "approval_date")
    private Date approvalDate;

    @JsonProperty(value = "publishing_status")
    private String publishingStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;


}
