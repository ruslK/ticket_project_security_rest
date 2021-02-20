package com.ticketsproject.servises;

import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.exception.TicketingProjectException;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();

    RoleDTO findById(long id) throws TicketingProjectException;

    RoleDTO findByDescription(String description);

}
