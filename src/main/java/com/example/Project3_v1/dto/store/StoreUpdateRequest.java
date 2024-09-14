package com.example.Project3_v1.dto.store;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUpdateRequest {
    private String storeName;
    private String introduction;
    private Integer classificationId;

}

