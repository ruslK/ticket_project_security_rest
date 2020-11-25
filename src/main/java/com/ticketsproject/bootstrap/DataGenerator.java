package com.ticketsproject.bootstrap;

import com.ticketsproject.dto.RoleDTO;
import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.enums.Gender;
import com.ticketsproject.servises.RoleService;
import com.ticketsproject.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements CommandLineRunner {


    RoleService roleService;
    UserService userService;

    @Autowired
    public DataGenerator(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.save(new RoleDTO(1L, "Administrator"));
        roleService.save(new RoleDTO(2L, "Manager"));
        roleService.save(new RoleDTO(3L, "Employee"));

        userService.save(new UserDTO("Job", "Misha", "job@misha.com", "password", true, "234523452345", roleService.findById(1L), Gender.FEMALE));
        userService.save(new UserDTO("Tina", "Ivanko", "tina@gmail.com", "password", true, "4356435643564356", roleService.findById(2L), Gender.FEMALE));
        userService.save(new UserDTO("Steve", "Shapiro", "steve@shapiro.com", "password", true, "4353454356", roleService.findById(3L), Gender.FEMALE));
    }
}
