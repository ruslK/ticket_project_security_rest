package com.ticketsproject.entities;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String gender;
    private String Role;
}
