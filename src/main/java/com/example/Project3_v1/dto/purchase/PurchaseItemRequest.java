package com.example.Project3_v1.dto.purchase;

import com.example.Project3_v1.entity.Product;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemRequest {
    private Integer productId;
    private Integer quantity;

}
