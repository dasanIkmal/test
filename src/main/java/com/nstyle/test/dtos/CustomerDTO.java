package com.nstyle.test.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private long id;
    private String name;
    private String email;
    private String phone;
}
