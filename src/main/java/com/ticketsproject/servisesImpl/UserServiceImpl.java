package com.ticketsproject.servisesImpl;

import com.ticketsproject.dto.UserDTO;
import com.ticketsproject.servises.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractMapService<UserDTO, String> implements UserService {

    @Override
    public UserDTO save(UserDTO user) {
        return super.save(user.getUserName(), user);
    }

    @Override
    public UserDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);

    }
    @Override
    public void delete(UserDTO object) {
        super.delete(object);
    }

    public List<UserDTO> getManagers() {
        return this.findAll();
    }
}
