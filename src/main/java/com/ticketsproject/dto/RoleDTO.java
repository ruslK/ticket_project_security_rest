package com.ticketsproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO {
    @JsonIgnore
    private Long id;
    private String description;
}
