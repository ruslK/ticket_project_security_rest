package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.entities.Role;
import com.ticketsproject.mapper.RoleMapper;
import com.ticketsproject.repository.RoleRepository;
import com.ticketsproject.servises.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return  roles.stream().map(roleMapper::convertToRoleDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(long id) {
        return roleMapper.convertToRoleDTO(roleRepository.findById(id).get());
    }
}
