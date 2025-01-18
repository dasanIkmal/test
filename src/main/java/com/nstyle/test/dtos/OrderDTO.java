package com.nstyle.test.dtos;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private long id;
    private long customerId;
    private List<Long> serviceIds;
    private long userId;
    private double totalBill;
    private String message;
}
