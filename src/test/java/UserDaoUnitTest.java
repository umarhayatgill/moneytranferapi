import dao.UserDao;
import dao.UserDaoImpl;
import exception.AlreadyExistException;
import exception.NotFoundException;
import model.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collection;

//in case of real database we would have to rollback the trasanctions after every test so that we leave database in the same state
//as it was before the tests as we do in case of exceptions at Dao Layer
@FixMethodOrder(MethodSorters.DEFAULT)
public class UserDaoUnitTest {
    private UserDao userDao = UserDaoImpl.getInstance();

    @Test
    public void getAllUsers(){
        Collection<User> users = userDao.getAllUsers();
        Assert.assertEquals(users.size(),2);
    }
    @Test
    public void createNewUser() throws AlreadyExistException {
        User user = User.builder().withId("3").withFirstName("umar").withLastName("hayat").withEmail("abc@kk.com").build();
        userDao.createUser(user);
        Collection<User> users = userDao.getAllUsers();
        Assert.assertEquals(users.size(),3);
    }


    @Test
    public void updateExistingUser() throws NotFoundException {
        User user = User.builder().withId("2").withFirstName("abc").withLastName("cde").withEmail("abc@kk.com").build();
        userDao.updateUser(user);
        User updatedUser = userDao.getUser("2");
        Assert.assertEquals(updatedUser.getFirstName(),"abc");
        Assert.assertEquals(updatedUser.getLastName(),"cde");
        Assert.assertEquals(updatedUser.getEmail(),"abc@kk.com");
    }

    @Test
    public void userExists(){
        Assert.assertEquals(true,userDao.userExist("1"));
    }

    @Test(expected = NotFoundException.class)
    public void deleteExistingUserThrowsExceptionIfInvalid() throws NotFoundException {
        userDao.deleteUser("4");
    }

}
