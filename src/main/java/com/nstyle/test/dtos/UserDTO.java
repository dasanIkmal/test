package com.nstyle.test.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String role;
    private String email;
    private String profileImg;
    private String cv;
    private String password;
    private LocalDate dob;
}
