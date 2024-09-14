package com.example.Project3_v1.RequestDto;

import com.example.Project3_v1.entity.Classification;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

