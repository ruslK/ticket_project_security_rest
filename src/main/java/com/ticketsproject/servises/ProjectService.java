package com.ticketsproject.servises;

import com.ticketsproject.dto.ProjectDTO;

public interface ProjectService extends GrudService<ProjectDTO, String> {
    void complete(ProjectDTO projectDTO);
}
