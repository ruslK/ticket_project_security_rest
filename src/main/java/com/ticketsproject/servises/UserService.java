package com.ticketsproject.servises;

import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.exception.TicketingProjectException;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    UserDTO save(UserDTO dto) throws TicketingProjectException;

    UserDTO update(UserDTO dto) throws TicketingProjectException;

    void delete(String username) throws TicketingProjectException;

    List<UserDTO> findAllManagers();

    List<UserDTO> findEmployees();

    Boolean checkIfUserCanBeDelete(User user);

    UserDTO confirm(User user);

    List<UserDTO> listAllByRole(String role);
}
