package com.nstyle.test.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer otp;
    private Long userID;
}
