package com.example.Project3_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String storeName;
    private String introduction;

    @ManyToOne
    private Classification classification;

    private String storeStatus; //Preparing, Open, Closing, Closed
    private String deleteReason;

    @OneToOne
    @JoinColumn(name = "owner")
    private User owner;

    @OneToMany(mappedBy = "store")
    private List<Product> products;
}
