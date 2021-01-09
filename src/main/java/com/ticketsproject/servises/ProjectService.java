package com.ticketsproject.servises;

import com.ticketsproject.dto.ProjectDTO;


import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectDTO> listOfProjects();

    void save(ProjectDTO dto);

    void deleteByProjectCode(String projectCode);

    ProjectDTO findByProjectCode(String projectCode);

    void complete(String projectCode);

    List<ProjectDTO> getAllProjectByManagerId ();

    List<ProjectDTO> listOfProjectsNonComplete();

}
