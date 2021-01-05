package com.ticketsproject.entities;

import com.ticketsproject.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
public class Project extends BaseEntity {

    private String projectName;
    private String projectCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetails;
    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private int completeCount;
    private int inCompleteCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User assignedManager;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Task> taskList = new ArrayList<>();
}
