package com.example.Project3_v1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference // Đặt ở phía con (Store)
    private User owner;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Product> products;
}
