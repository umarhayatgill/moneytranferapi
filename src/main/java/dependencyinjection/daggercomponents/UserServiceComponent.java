package dependencyinjection.daggercomponents;

import javax.inject.Singleton;

import dagger.Component;
import dependencyinjection.daggermodules.UserServiceModule;
import service.UserServiceImpl;

@Component(modules = UserServiceModule.class)
public interface UserServiceComponent {
    UserServiceImpl buildUserService();
}
