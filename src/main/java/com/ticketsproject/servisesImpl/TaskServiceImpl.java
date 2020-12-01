package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.enums.Status;
import com.ticketsproject.servises.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO, Long> implements TaskService {

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        if(super.findById(taskDTO.getId()) == null) {
            taskDTO.setId(UUID.randomUUID().getMostSignificantBits());
            if(taskDTO.getStatus() == null) taskDTO.setStatus(Status.OPEN);
            if(taskDTO.getAssignedDate() == null) taskDTO.setAssignedDate(LocalDate.now());
        } else {
            taskDTO.setStatus(super.findById(taskDTO.getId()).getStatus());
            taskDTO.setAssignedDate(super.findById(taskDTO.getId()).getAssignedDate());
            taskDTO.setLastUpdateDate(LocalDate.now());
        }
        return super.save(taskDTO.getId(), taskDTO);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO object) {
        super.update(object.getId(), object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(TaskDTO object) {
        super.delete(object);
    }
}
