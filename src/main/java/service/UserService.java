package service;

import exception.AlreadyExistException;
import exception.NotFoundException;
import model.User;

import java.util.Collection;
import java.util.Map;

public interface UserService {
    Collection<User> getAllUsers();

    User getUser(String email) throws NotFoundException;

    void createUser(User user) throws AlreadyExistException;

    User updateUser(User user) throws NotFoundException;

    boolean userExist(String userId);

    void deleteUser(String userId) throws NotFoundException;
}
