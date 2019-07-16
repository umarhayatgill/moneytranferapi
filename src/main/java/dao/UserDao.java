package dao;

import exception.AlreadyExistException;
import exception.NotFoundException;
import model.User;

import java.util.Collection;
import java.util.Map;

public interface UserDao {
    Collection<User> getAllUsers();

    User getUser(String emailId) throws NotFoundException;

    void createUser(User user) throws AlreadyExistException;

    User updateUser(User user) throws NotFoundException;

    boolean userExist(String id);

    void deleteUser(String userId) throws NotFoundException;
}
