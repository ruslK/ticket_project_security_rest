package com.ticketsproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ticketsproject.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class TaskDTO {
    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetails;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdateDate;
    private Status status;
}
