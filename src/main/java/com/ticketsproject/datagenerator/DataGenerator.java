package com.ticketsproject.datagenerator;

import com.ticketsproject.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataGenerator {

    public DataGenerator() {
        this.listOfUsers.add(new User("Job", "Misha", "job@misha.com", "234523452345", "tipaPasssword", "MALE", "Employee"));
        this.listOfUsers.add(new User("Tina", "Ivanko", "tina@gmail.com", "5436345634646", "tipaPasssword", "FEMALE", "Manager"));
        this.listOfUsers.add( new User("Steve", "Shapiro", "steve@shapiro.com", "34554345345", "tipaPasssword", "MALE", "Administrator"));
    }

    public final static List<String> ROLES = Arrays.asList("Administrator", "Manager"," Employee");
    private List<User> listOfUsers = new ArrayList<>()   ;


    public List<User> getListOfUsers () {
        return this.listOfUsers;
    }

    public void setListOfUsers(User newUser) {
        this.listOfUsers.add(newUser);
    }
}
