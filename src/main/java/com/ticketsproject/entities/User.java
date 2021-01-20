package com.ticketsproject.entities;

import com.ticketsproject.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    public User(String firstName, String lastName, String userName, String phone, String password, boolean enabled, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.enabled = enabled;
        this.gender = gender;
    }

    private String firstName;
    private String lastName;
    private String userName;
    private String phone;
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
