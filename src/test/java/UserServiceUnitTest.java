import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    UserDao userDao = mock(UserDao.class);
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void callDaoGetUsersWhenServiceGetUsersIsCalled(){
        given(userDao.getAllUsers()).willReturn(null);
        userServiceImpl.getAllUsers();
        verify(userDao,times(1)).getAllUsers();
    }
    @Test
    public void callDaoGetUserByIdWhenServiceGetUserByIdIsCalled() throws NotFoundException {
        given(userDao.getUser("1")).willReturn(null);
        userServiceImpl.getUser("1");
        verify(userDao,times(1)).getUser("1");
    }
    @Test
    public void callDaoCreateUserWhenServiceCreateUserByIdIsCalled() throws AlreadyExistException {
        userServiceImpl.createUser(null);
        verify(userDao,times(1)).createUser(null);
    }
    @Test
    public void callDaoUpdateUserWhenServiceUpdateUserIsCalled() throws NotFoundException {
        given(userDao.updateUser(null)).willReturn(null);
        userServiceImpl.updateUser(null);
        verify(userDao,times(1)).updateUser(null);
    }
}
