package com.ticketsproject.repository;

import com.ticketsproject.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findAllByProjectCode(String projectCode);
}
