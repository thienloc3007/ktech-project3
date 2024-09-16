package com.example.Project3_v1.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String name;
    private String image;
    private String description;
    private Integer price;
    private Integer stock;
}
