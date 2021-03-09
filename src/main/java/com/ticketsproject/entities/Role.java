package com.ticketsproject.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table (name = "roles")
@Builder
public class Role extends BaseEntity{
    private String description;

    public Role(Long id, String description) {
        this.description = description;
        super.setId(id);
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Role{" +
                "description='" + description + '\'' +
                '}';
    }
}
