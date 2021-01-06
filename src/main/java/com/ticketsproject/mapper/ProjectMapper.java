package com.ticketsproject.mapper;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.User;
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
        User manager = userRepository.findByUserName(dto.getAssignedManager().getUserName());
        Project project = modelMapper.map(dto, Project.class);
        project.setAssignedManager(manager);
        return project;
    }

    public ProjectDTO convertToDto(Project entity) {
        return modelMapper.map(entity, ProjectDTO.class
        );
    }
}
