package com.ticketsproject.servises;

import com.ticketsproject.dto.UserDTO;

import java.util.List;

public interface UserService extends GrudService<UserDTO, String> {
    List<UserDTO> findManagers();

    List<UserDTO> findEmployees();
}
