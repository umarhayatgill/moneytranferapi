package service;

import model.User;

import java.util.List;

public interface UserService {
    // returns a list of all users
    public List<User> getAllUsers();

    // returns a single user by id
    public User getUser(String id);

    // creates a new user
    public User createUser(String name, String email);

    // updates an existing user
    public User updateUser(String id, String name, String email);
}
