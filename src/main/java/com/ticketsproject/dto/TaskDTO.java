package com.ticketsproject.dto;

import com.ticketsproject.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
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

    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetails, LocalDate assignedDate, LocalDate lastUpdateDate, Status status) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetails = taskDetails;
        this.assignedDate = assignedDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }
}
