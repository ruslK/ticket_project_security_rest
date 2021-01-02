package com.ticketsproject.mapper;

import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO dto) {
        return modelMapper.map(dto, Role.class);
    }

    public RoleDTO convertToRoleDTO(Role entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }
}
