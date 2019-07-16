package service;

import dao.UserDao;
import dao.UserDaoImpl;
import exception.AlreadyExistException;
import exception.NotFoundException;
import model.User;

import java.util.Collection;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public Collection<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUser(String id) throws NotFoundException {
        return userDao.getUser(id);
    }

    @Override
    public void createUser(User user) throws AlreadyExistException {
        userDao.createUser(user);
    }
    @Override
    public User updateUser(User user) throws NotFoundException {
        return userDao.updateUser(user);
    }

    @Override
    public boolean userExist(final String id){
        return userDao.userExist(id);
    }

    @Override
    public void deleteUser(final String userId) throws NotFoundException {
        userDao.deleteUser(userId);
    }
}
