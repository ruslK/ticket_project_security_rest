package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.enums.Status;
import com.ticketsproject.mapper.ProjectMapper;
import com.ticketsproject.mapper.UserMapper;
import com.ticketsproject.repository.ProjectRepository;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.servises.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<ProjectDTO> listOfProjects() {
        return projectRepository.findAll(Sort.by("projectCode"))
                .stream()
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        Project project = projectRepository.findAllByProjectCode(dto.getProjectCode());

        if (project != null) {
            Long id = project.getId();
            Project updatedProject = projectMapper.convertToEntity(dto);
            updatedProject.setProjectStatus(project.getProjectStatus());
            updatedProject.setId(id);
            projectRepository.save(updatedProject);
        } else {
            Project project1 = projectMapper.convertToEntity(dto);
            project1.setProjectStatus(Status.OPEN);
            projectRepository.save(project1);
        }
    }

    @Override
    public void deleteByProjectCode(String projectCode) {
        projectRepository.delete(projectRepository.findAllByProjectCode(projectCode));
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        return projectMapper.convertToDto(projectRepository.findAllByProjectCode(projectCode));
    }

    @Override
    public void complete(ProjectDTO dto) {
        Project project = projectRepository.findAllByProjectCode(dto.getProjectCode());
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}
