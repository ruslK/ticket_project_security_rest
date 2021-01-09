package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.Task;
import com.ticketsproject.entities.User;
import com.ticketsproject.enums.Status;
import com.ticketsproject.mapper.TaskMapper;
import com.ticketsproject.repository.ProjectRepository;
import com.ticketsproject.repository.TaskRepository;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.servises.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findTaskById(Long id) {
        return taskMapper.convertToDTO(taskRepository.findById(id).get());
    }

    @Override
    public List<TaskDTO> listOfTasks() {
        return taskRepository.findAll(Sort.by("id"))
                .stream()
                .map(taskMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        Task taskFomUI = taskMapper.convertToEntity(dto);
        if (dto.getId() == null) {
            taskFomUI.setAssignedDate(LocalDate.now());
            taskFomUI.setStatus(Status.OPEN);
        } else {
            Task task = taskRepository.findById(taskFomUI.getId()).get();
            if(taskFomUI.getStatus() == null) {
                taskFomUI.setStatus(task.getStatus());
            }
            taskFomUI.setLastUpdateDate(LocalDate.now());
            taskFomUI.setAssignedDate(task.getAssignedDate());
        }
        taskRepository.save(taskFomUI);
    }

    @Override
    public void deleteByID(Long id) {
        Task task = taskRepository.findById(id).get();
        task.setIsDeleted(true);
        taskRepository.save(task);
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTask(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTask(projectCode);
    }

    @Override
    public void deleteByProject(Project project) {
        List<TaskDTO> tasks = taskRepository.findAllByProjectProjectCode(project.getProjectCode())
                .stream().map(taskMapper :: convertToDTO).collect(Collectors.toList());
        for(TaskDTO t: tasks) this.deleteByID(t.getId());
    }

    @Override
    public List<TaskDTO> listAllTaskByStatusIsNot(Status status) {
        User user = userRepository.findByUserName("havybygy");
        List<Task> list = taskRepository.findAllByStatusIsNotAndAssignedEmployee(status, user);
        return list.stream().map(taskMapper :: convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTaskByProjectManager() {
        User user = userRepository.findByUserName("ruslan@kasymov");
        List<Task> tasks = taskRepository.findAllByProjectAssignedManager(user);
        return tasks.stream().map(taskMapper :: convertToDTO).collect(Collectors.toList());
    }
}
