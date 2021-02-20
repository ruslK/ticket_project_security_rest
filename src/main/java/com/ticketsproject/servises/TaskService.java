package com.ticketsproject.servises;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.entities.Project;
import com.ticketsproject.entities.User;
import com.ticketsproject.enums.Status;
import com.ticketsproject.exception.TicketingProjectException;

import java.util.List;

public interface TaskService {

    TaskDTO findTaskById (Long id) throws TicketingProjectException;

    void deleteByID (Long id) throws TicketingProjectException;

    List<TaskDTO> listOfTasks();

    TaskDTO save(TaskDTO dto);
    TaskDTO update(TaskDTO dto) throws TicketingProjectException;

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    void deleteByProject(Project project) throws TicketingProjectException;

    List<TaskDTO> listAllTaskByStatusIsNot(Status status) throws TicketingProjectException;

    List<TaskDTO> listAllTaskByProjectManager() throws TicketingProjectException;

    List<TaskDTO> listAllTaskByStatus(Status status) throws TicketingProjectException;

    List<TaskDTO> getAllTaskByEmployee(User user);

    TaskDTO updateStatus (TaskDTO taskDTO) throws TicketingProjectException;
}
