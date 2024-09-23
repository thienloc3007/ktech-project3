package com.example.Project3_v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "buyer")
    @JsonIgnore
    private User buyer;

    @OneToMany (mappedBy = "purchaseBill")
    private List<PurchaseItem> purchaseItems;

    private String purchaseStatus; //NO PURCHASE, REQUEST TO PAYMENT, PAID, CANCELED
    private Integer totalAmount;
}