package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.enums.Status;
import com.ticketsproject.servises.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        if(super.findById(projectDTO.getProjectCode()) == null) {
            if(projectDTO.getProjectStatus() == null) projectDTO.setProjectStatus(Status.OPEN);
        } else {
            projectDTO.setProjectStatus(super.findById(projectDTO.getProjectCode()).getProjectStatus());
        }
        return super.save(projectDTO.getProjectCode(), projectDTO);
    }

    @Override
    public ProjectDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public void delete(ProjectDTO object) {
        super.delete(object);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.COMPLETE);
        super.save(projectDTO.getProjectCode(), projectDTO);
    }

    @Override
    public void update(ProjectDTO object) {
        ProjectDTO newProject = findById(object.getProjectCode());
        if (object.getProjectStatus() == null) {
            object.setProjectStatus(newProject.getProjectStatus());
        }
        super.update(object.getProjectCode(), object);
    }
}
