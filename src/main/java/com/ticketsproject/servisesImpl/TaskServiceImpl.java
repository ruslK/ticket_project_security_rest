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

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper,
                           ProjectRepository projectRepository, UserRepository userRepository) {
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
    public void deleteByID(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> listOfTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        Project project = projectRepository.findAllByProjectCode(dto.getProject().getProjectCode());
        User user = userRepository.findByUserName(dto.getAssignedEmployee().getUserName());
        Task taskFomUI = taskMapper.convertToEntity(dto);
        taskFomUI.setProject(project);
        taskFomUI.setAssignedEmployee(user);

        if (dto.getId() == null) {
            taskFomUI.setAssignedDate(LocalDate.now());
            taskFomUI.setStatus(Status.OPEN);
        } else {
            Task task = taskRepository.findById(dto.getId()).get();
            taskFomUI.setId(task.getId());
            taskFomUI.setStatus(task.getStatus());
            taskFomUI.setLastUpdateDate(LocalDate.now());
            taskFomUI.setAssignedDate(task.getAssignedDate());
        }
        taskRepository.save(taskFomUI);
    }
}
