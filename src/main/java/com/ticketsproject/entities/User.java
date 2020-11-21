package com.ticketsproject.entities;

import com.ticketsproject.enums.Gender;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Gender gender;
    private Role role;

    public User(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lustUpdateUserId, String firstName, String lastName, String email, String phone, String password, Gender gender, Role role) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lustUpdateUserId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }
}
