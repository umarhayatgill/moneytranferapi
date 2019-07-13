package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import java.util.List;



public class UserServiceImpl implements UserService {
    private UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUser(String emailId) throws Exception {
        return userDao.getUser(emailId);
    }

    @Override
    public User createUser(String name, String emailId) throws Exception {
        return userDao.createUser(name, emailId);
    }

    @Override
    public User updateUser(String name, String emailId) {
        return null;
    }
}
