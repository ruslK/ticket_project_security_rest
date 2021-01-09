package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.enums.Status;
import com.ticketsproject.mapper.ProjectMapper;
import com.ticketsproject.repository.ProjectRepository;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.TaskService;
import com.ticketsproject.servises.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final TaskService taskService;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.taskService = taskService;
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
        Project project = projectRepository.findAllByProjectCode(projectCode);
        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);

        taskService.deleteByProject(project);
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        return projectMapper.convertToDto(projectRepository.findAllByProjectCode(projectCode));
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findAllByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> getAllProjectByManagerId() {
        UserDTO manager = userService.findByUserName("ruslan@kasymov");
        return projectRepository.findAllByManagerId(manager.getId()).stream()
                .map(project -> {
                    ProjectDTO obj = projectMapper.convertToDto(project);
                    obj.setCompleteCount(taskService.totalCompletedTask(obj.getProjectCode()));
                    obj.setInCompleteCount(taskService.totalNonCompletedTask(obj.getProjectCode()));
                    return obj;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listOfProjectsNonComplete() {
        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream().map(projectMapper :: convertToDto)
                .collect(Collectors.toList());
    }
}
