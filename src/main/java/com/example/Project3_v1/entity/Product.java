package com.example.Project3_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    private String description;
    private Integer price;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "store")
    private Store store;

}
