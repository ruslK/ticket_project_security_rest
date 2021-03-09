package com.ticketsproject.servises;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.User;
import com.ticketsproject.exception.AccessDeniedException;
import com.ticketsproject.exception.TicketingProjectException;


import java.util.List;

public interface ProjectService {
    List<ProjectDTO> listOfProjects();

    ProjectDTO save(ProjectDTO project) throws TicketingProjectException, AccessDeniedException;
    ProjectDTO update(ProjectDTO dto) throws TicketingProjectException, AccessDeniedException;

    void deleteByProjectCode(String projectCode) throws TicketingProjectException;

    ProjectDTO findByProjectCode(String projectCode) throws TicketingProjectException;

    void complete(String projectCode);

    List<ProjectDTO> getAllProjectByManagerId();

    List<ProjectDTO> listOfProjectsNonComplete();

    List<ProjectDTO> getAllByAssignedManager(User user);

}
