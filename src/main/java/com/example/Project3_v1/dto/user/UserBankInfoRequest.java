package com.example.Project3_v1.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBankInfoRequest {
    private String bankName;
    private Integer balance;
    private String accountNumber;
}
