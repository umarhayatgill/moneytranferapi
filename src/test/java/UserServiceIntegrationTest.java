import dependencyinjection.daggercomponents.DaggerUserServiceComponent;
import dependencyinjection.daggercomponents.UserServiceComponent;
import exception.AlreadyExistException;
import exception.NotFoundException;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import service.UserService;

import java.util.Collection;

//final block in the end of every test to perform rollback manually since for the simplicity of in memory datastore
//i did not use real database where i could have used database rollback feature to declare tests to rollback after each test
public class UserServiceIntegrationTest {
    UserServiceComponent userServiceComponent = DaggerUserServiceComponent.create();
    UserService userService = userServiceComponent.buildUserService();

    @Test
    public void getAllAccounts(){
        Collection<User> users = userService.getAllUsers();
        Assert.assertEquals(users.size(),2);
    }

    @Test
    public void createNewAccount() throws AlreadyExistException, NotFoundException {
        try {
            User user = User.builder().withId("3").withFirstName("test").withLastName("test")
                    .withEmail("a@abc.com").build();
            userService.createUser(user);
            Collection<User> users = userService.getAllUsers();
            Assert.assertEquals(users.size(), 3);
        }finally {
            userService.deleteUser("3");
        }
    }

    @Test
    public void updateNewAccount() throws AlreadyExistException, NotFoundException {
        try {
            User user = User.builder().withId("2").withFirstName("test").withLastName("test")
                    .withEmail("a@abc.com").build();
            User updatedUser = userService.updateUser(user);
            Assert.assertEquals("test", updatedUser.getFirstName());
            Assert.assertEquals("test", updatedUser.getLastName());
            Assert.assertEquals("a@abc.com", updatedUser.getEmail());
        }finally {
            User user = User.builder().withId("2").withFirstName("anotherfoo")
                    .withLastName("anotherbar").withEmail("anotherfoo@anotherbar.com").build();
            userService.updateUser(user);
        }
    }

    @Test
    public void deleteUser() throws NotFoundException, AlreadyExistException {
        try {
            userService.deleteUser("2");
            Collection<User> accounts = userService.getAllUsers();
            Assert.assertEquals(accounts.size(), 1);
        }finally {
            User user = User.builder().withId("2").withFirstName("anotherfoo")
                    .withLastName("anotherbar").withEmail("anotherfoo@anotherbar.com").build();
            userService.createUser(user);
        }
    }
}
