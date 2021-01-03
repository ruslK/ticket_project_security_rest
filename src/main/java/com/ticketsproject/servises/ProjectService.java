package com.ticketsproject.servises;

import com.ticketsproject.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectDTO> listOfProjects();

    void save(ProjectDTO dto);

    void deleteByProjectCode(String projectCode);

    ProjectDTO findByProjectCode(String projectCode);

    void complete(ProjectDTO dto);
}
