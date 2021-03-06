package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.User;
import com.ticketsproject.enums.Status;
import com.ticketsproject.exception.AccessDeniedException;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.repository.ProjectRepository;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.TaskService;
import com.ticketsproject.servises.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final MapperUtil mapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService,
                              TaskService taskService, MapperUtil mapper) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @Override
    public List<ProjectDTO> listOfProjects() {
        return projectRepository.findAll(Sort.by("projectCode"))
                .stream()
                .map(project -> mapper.convert(project, new ProjectDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO save(ProjectDTO project) throws TicketingProjectException, AccessDeniedException {
        Project foundProject = projectRepository.findAllByProjectCode(project.getProjectCode());

        if (foundProject != null) {
            throw new TicketingProjectException("Project with " + project.getProjectCode() + " already exist");
        }
        UserDTO manager = userService.findByUserName(project.getAssignedManager().getUserName());
        project.setProjectStatus(Status.OPEN);
        project.setAssignedManager(manager);
        return mapper.convert(projectRepository.save(mapper.convert(project, new Project())), new ProjectDTO());
    }

    @Override
    public ProjectDTO update(ProjectDTO dto) throws TicketingProjectException, AccessDeniedException {
        Project project = projectRepository.findAllByProjectCode(dto.getProjectCode());

        if (project == null) {
            throw new TicketingProjectException("Project is not exist");
        }
        Project convertedProject = mapper.convert(dto, new Project());
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());
        convertedProject.setAssignedManager(
                mapper.convert(userService.findByUserName(dto.getAssignedManager().getUserName()), new User()));
        return mapper.convert(projectRepository.save(convertedProject), new ProjectDTO());
    }

    @Override
    public void deleteByProjectCode(String projectCode) throws TicketingProjectException {
        Project project = projectRepository.findAllByProjectCode(projectCode);
        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);

        taskService.deleteByProject(project);
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) throws TicketingProjectException {
        Project prj = projectRepository.findByProjectCode(projectCode);
        if (prj == null) {
            throw new TicketingProjectException("Project with Project Code: " + projectCode + " not found");
        }
        return mapper.convert(prj, new ProjectDTO());
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findAllByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> getAllProjectByManagerId() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());


        return projectRepository.findAllByManagerId(id).stream()
                .map(project -> {
                    ProjectDTO obj = mapper.convert(project, new ProjectDTO());
                    obj.setCompleteCount(taskService.totalCompletedTask(obj.getProjectCode()));
                    obj.setInCompleteCount(taskService.totalNonCompletedTask(obj.getProjectCode()));
                    return obj;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listOfProjectsNonComplete() {
        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream().map(project -> mapper.convert(project, new ProjectDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getAllByAssignedManager(User user) {
        return projectRepository.findAllByManagerId(user.getId())
                .stream().map(project -> mapper.convert(project, new ProjectDTO())).collect(Collectors.toList());
    }
}
