package com.ticketsproject.servises;

import com.ticketsproject.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO findTaskById (Long id);

    void deleteByID (Long id);

    List<TaskDTO> listOfTasks();

    void save(TaskDTO dto);
}
