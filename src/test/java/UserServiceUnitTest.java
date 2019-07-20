import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import dao.UserDao;
import exception.AlreadyExistException;
import exception.NotFoundException;
import service.UserServiceImpl;

import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    UserDao userDao = mock(UserDao.class);
    User user = mock(User.class);
    Collection<User> userCollection = mock(Collection.class);
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void callDaoGetUsersWhenServiceGetUsersIsCalled(){
        given(userDao.getAllUsers()).willReturn(userCollection);
        userServiceImpl.getAllUsers();
        verify(userDao,times(1)).getAllUsers();
    }
    @Test
    public void callDaoGetUserByIdWhenServiceGetUserByIdIsCalled() throws NotFoundException {
        given(userDao.getUser("1")).willReturn(user);
        userServiceImpl.getUser("1");
        verify(userDao,times(1)).getUser("1");
    }
    @Test
    public void callDaoCreateUserWhenServiceCreateUserByIdIsCalled() throws AlreadyExistException {
        userServiceImpl.createUser(user);
        verify(userDao,times(1)).createUser(user);
    }
    @Test
    public void callDaoUpdateUserWhenServiceUpdateUserIsCalled() throws NotFoundException {
        given(userDao.updateUser(user)).willReturn(user);
        userServiceImpl.updateUser(user);
        verify(userDao,times(1)).updateUser(user);
    }
}
