package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.mapper.UserMapper;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.servises.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository.findAll(Sort.by("firstName")).stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDTO(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO dto) {
        User user = userRepository.findByUserName(dto.getUserName());
        if (user != null) {
            Long id = user.getId();
            User updatedUser = userMapper.convertToEntity(dto);
            updatedUser.setId(id);
            userRepository.save(updatedUser);
        } else {
            userRepository.save(userMapper.convertToEntity(dto));
        }
    }

    @Override
    public UserDTO update(UserDTO dto) {
        return null;
    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllManagers() {
        return userRepository.listOfManagers().stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findEmployees() {
        return userRepository.listOfEmployees().stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
