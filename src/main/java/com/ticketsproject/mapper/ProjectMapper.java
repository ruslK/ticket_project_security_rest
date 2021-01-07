package com.ticketsproject.mapper;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ProjectMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Project convertToEntity(ProjectDTO dto) {
        return modelMapper.map(dto, Project.class);
    }

    public ProjectDTO convertToDto(Project entity) {
        return modelMapper.map(entity, ProjectDTO.class
        );
    }
}
