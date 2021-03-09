package com.ticketsproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticketsproject.enums.Status;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "is_deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public class Project extends BaseEntity {

    private String projectName;
    @Column(unique = true)
    private String projectCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetails;
    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private int completeCount;
    private int inCompleteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User assignedManager;
}
