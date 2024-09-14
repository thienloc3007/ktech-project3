package com.example.Project3_v1.RequestDto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCreateRequest {
    private String storeName;
    private String introduction;
    private Integer classificationId;
    private String storeStatus;
}
