package service;

import exception.NotFoundException;
import model.User;

import java.util.List;

public interface UserService {
    // returns a list of all users
    public List<User> getAllUsers();

    // returns a single user by id
    public User getUser(String emailId) throws Exception;

    // creates a new user
    public User createUser(String name, String emailId) throws Exception;

    // updates an existing user
    public User updateUser(String name, String emailId) throws NotFoundException;
}
