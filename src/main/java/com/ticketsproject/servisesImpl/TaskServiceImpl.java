package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.entities.Task;
import com.ticketsproject.enums.Status;
import com.ticketsproject.mapper.TaskMapper;
import com.ticketsproject.repository.ProjectRepository;
import com.ticketsproject.repository.TaskRepository;
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

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
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
            taskFomUI.setStatus(task.getStatus());
            taskFomUI.setLastUpdateDate(LocalDate.now());
            taskFomUI.setAssignedDate(task.getAssignedDate());
        }
        if(taskFomUI.getProject().getProjectStatus().getValue().equals("Complete")) {
            taskFomUI.getProject().setCompleteCount(taskFomUI.getProject().getCompleteCount() + 1);
        } else {
            taskFomUI.getProject().setInCompleteCount(taskFomUI.getProject().getInCompleteCount() + 1);
        }
        projectRepository.save(taskFomUI.getProject());
        taskRepository.save(taskFomUI);
    }

    @Override
    public void deleteByID(Long id) {
        Task task = taskRepository.findById(id).get();
        task.setIsDeleted(true);
        taskRepository.save(task);
    }
}
