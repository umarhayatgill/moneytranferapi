package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl userDaoImpl = null;

    User user;
    List<User> list = new ArrayList<>();
    public UserDaoImpl(){
        user = new User();
        user.setEmail("firstuser@abc.com");
        user.setName("firstuser");
        list.add(user);
        user = new User();
        user.setEmail("seconduser@abc.com");
        user.setName("seconduser");
        list.add(user);
    }

    //we can place synchronized inside method as well with double lock checking
    // to increase performance but i have placed it at method level for sake of simplicity of this task.
    public static synchronized UserDaoImpl getInstance(){
        if(userDaoImpl==null){
            userDaoImpl = new UserDaoImpl();
        }
        return userDaoImpl;
    }

    @Override
    public List<User> getAllUsers() {
        return list;
    }

    @Override
    public User getUser(String emailId) throws Exception {
        return list.stream().findAny().filter(user -> user.getEmail().equals(emailId))
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User createUser(String name, String emailId) throws Exception {
        if(list.stream().anyMatch(user -> user.getEmail().trim().equals(emailId))){
            throw new Exception("User already exists");
        }else {
            user = new User();
            user.setName(name);
            user.setEmail(emailId);
            list.add(user);
        }
        return user;
    }

    @Override
    public User updateUser(String name, String emailId) {
        return null;
    }
}
