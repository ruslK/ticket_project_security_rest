//package com.ticketsproject.bootstarap;
//
//import com.ticketsproject.entities.Role;
//import com.ticketsproject.entities.User;
//import com.ticketsproject.enums.Gender;
//import com.ticketsproject.repository.RoleRepository;
//import com.ticketsproject.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DataGenerator implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;
//
//    public DataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Role rAdmin = new Role();
//        Role rManager= new Role();
//        Role eEmployee= new Role();
//        rAdmin.setDescription("Admin");
//        rManager.setDescription("Manager");
//        eEmployee.setDescription("Employee");
//        roleRepository.save(rAdmin);
//        roleRepository.save(rManager);
//        roleRepository.save(eEmployee);
//
//        User admin = new User("Admin", "Admin", "admin", "5434555454",
//                passwordEncoder.encode("admin"), true, Gender.MALE);
//
//        User manager = new User("Manager", "Manager", "manager", "5434555454",
//                passwordEncoder.encode("manager"), true, Gender.FEMALE);
//
//        admin.setRole(rAdmin);
//        manager.setRole(rManager);
//
//        userRepository.save(admin);
//        userRepository.save(manager);
//    }
//}
