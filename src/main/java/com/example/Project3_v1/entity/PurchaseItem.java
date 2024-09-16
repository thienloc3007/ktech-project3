package com.example.Project3_v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
//    @JoinColumn(name = "product")
//    @JsonIgnore
    private Product product;
    private Integer quantity;

    @ManyToOne
    @JsonIgnore
    private PurchaseBill purchaseBill;
}
