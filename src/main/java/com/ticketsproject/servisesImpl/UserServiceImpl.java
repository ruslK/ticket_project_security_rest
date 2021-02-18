package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.ProjectDTO;
import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.dto.TaskDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.entities.User;
import com.ticketsproject.exception.TicketingProjectException;
import com.ticketsproject.mapper.MapperUtil;
import com.ticketsproject.repository.UserRepository;
import com.ticketsproject.servises.ProjectService;
import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.TaskService;
import com.ticketsproject.servises.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserServiceImpl(@Lazy UserRepository userRepository,
                           MapperUtil mapperUtil, TaskService taskService,
                           @Lazy ProjectService projectService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.taskService = taskService;
        this.projectService = projectService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository.findAll(Sort.by("firstName")).stream()
                .map(obj -> mapperUtil.convert(obj, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return mapperUtil.convert(userRepository.findByUserName(username), new UserDTO());
    }

    @Override
    @Transactional
    public UserDTO save(UserDTO dto) throws TicketingProjectException {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userRepository.findByUserName(dto.getUserName());

        if (user != null) {
            throw new TicketingProjectException("User with username: " + dto.getUserName() + " is already exist");
        } else {
            dto.setRole(roleService.findByDescription(dto.getRole().getDescription()));
            return mapperUtil
                    .convert(userRepository.save(mapperUtil.convert(dto, new User())), new UserDTO());
        }
    }

    @Override
    public UserDTO update(UserDTO dto) throws TicketingProjectException {

        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());

        if(user == null){
            throw new TicketingProjectException("User Does Not Exists");
        }
        //Map update user dto to entity object
        User convertedUser = mapperUtil.convert(dto,new User());
        convertedUser.setPassword(passwordEncoder.encode(convertedUser.getPassword()));
        convertedUser.setEnabled(true);

        //set id to the converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void delete(String username) throws TicketingProjectException {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new TicketingProjectException("User " + username + " does not Exists in the DB");
        }

        if (!checkIfUserCanBeDelete(user)) {
            throw new TicketingProjectException("User " + username + " can not be delete from UI, " +
                    "It is linked projects or tasks");
        }
        user.setUserName(user.getUserName() + "-" + user.getId());
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllManagers() {
        return userRepository.listOfManagers().stream()
                .map(obj -> mapperUtil.convert(obj, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findEmployees() {
        return userRepository.listOfEmployees().stream()
                .map(obj -> mapperUtil.convert(obj, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkIfUserCanBeDelete(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOS = projectService.getAllByAssignedManager(user);
                return projectDTOS.size() == 0;
            case "Employee":
                List<TaskDTO> tasks = taskService.getAllTaskByEmployee(user);
                return tasks.size() == 0;
            default:
                return true;
        }
    }

    @Override
    public UserDTO confirm(User user) {
        user.setEnabled(true);
        User foundUser = userRepository.save(user);
        return mapperUtil.convert(foundUser, new UserDTO());


    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(obj -> {return mapperUtil.convert(obj,new UserDTO());}).collect(Collectors.toList());
    }
}
