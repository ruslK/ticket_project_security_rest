package com.ticketsproject.servises;

import com.ticketsproject.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO dto);

    UserDTO update(UserDTO dto);

    void delete(String username);

    List<UserDTO> findAllManagers();

    List<UserDTO> findEmployees();
}
