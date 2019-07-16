package binding;

import com.google.inject.AbstractModule;
import controller.UserController;
import service.UserService;
import service.UserServiceImpl;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
    }
}