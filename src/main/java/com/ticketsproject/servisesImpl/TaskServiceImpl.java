package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.Task;
import com.ticketsproject.entities.User;
import com.ticketsproject.enums.Status;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.repository.TaskRepository;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.servises.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final MapperUtil mapperUtil;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(MapperUtil mapperUtil, TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.mapperUtil = mapperUtil;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findTaskById(Long id) throws TicketingProjectException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TicketingProjectException("Task does not exist"));
        return mapperUtil.convert(task, new TaskDTO());
    }

    @Override
    public List<TaskDTO> listOfTasks() {
        return taskRepository.findAll(Sort.by("id"))
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO save(TaskDTO dto) {
        dto.setStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskRepository.save(mapperUtil.convert(dto, new Task()));
        return mapperUtil.convert(task, new TaskDTO());
    }

    @Override
    public TaskDTO update(TaskDTO dto) throws TicketingProjectException {
        taskRepository.findById(dto.getId())
                .orElseThrow(() -> new TicketingProjectException("Task does not exist"));
        Task convertedTask = mapperUtil.convert(dto, new Task());
        return mapperUtil.convert(taskRepository.save(convertedTask), new TaskDTO());
    }

    @Override
    public TaskDTO updateStatus(TaskDTO taskDTO) throws TicketingProjectException {
        taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new TicketingProjectException("Task does not exit"));
        Task convertedTask = mapperUtil.convert(taskDTO, new Task());
        Task save = taskRepository.save(convertedTask);
        return mapperUtil.convert(save, new TaskDTO());
    }

    @Override
    public void deleteByID(Long id) throws TicketingProjectException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TicketingProjectException("Task does not exist"));
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
    public void deleteByProject(Project project) throws TicketingProjectException {
        List<TaskDTO> tasks = taskRepository.findAllByProjectProjectCode(project.getProjectCode())
                .stream().map(task -> mapperUtil.convert(task, new TaskDTO())).collect(Collectors.toList());
        for (TaskDTO t : tasks) this.deleteByID(t.getId());
    }

    @Override
    public List<TaskDTO> listAllTaskByStatusIsNot(Status status) throws TicketingProjectException {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findById(id).orElseThrow(() -> new TicketingProjectException("This user does not exist"));
        List<Task> list = taskRepository.findAllByStatusIsNotAndAssignedEmployee(status, user);
        return list.stream().map(task -> mapperUtil.convert(task, new TaskDTO())).collect(Collectors.toList());
    }


    @Override
    public List<TaskDTO> listAllTaskByProjectManager() throws TicketingProjectException {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findById(id).orElseThrow(() -> new TicketingProjectException("This user does not exist"));
        List<Task> tasks = taskRepository.findAllByProjectAssignedManager(user);
        return tasks.stream().map(task -> mapperUtil.convert(task, new TaskDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTaskByStatus(Status status) throws TicketingProjectException {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findById(id).orElseThrow(() -> new TicketingProjectException("This user does not exist"));
        List<Task> list = taskRepository.findAllByStatusIsAndAssignedEmployee(status, user);
        return list.stream().map(task -> mapperUtil.convert(task, new TaskDTO())).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllTaskByEmployee(User user) {
        return taskRepository.findAllByAssignedEmployee(user)
                .stream().map(task -> mapperUtil.convert(task, new TaskDTO())).collect(Collectors.toList());
    }


}
