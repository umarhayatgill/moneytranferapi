package controller;

import javax.inject.Singleton;

import dagger.Component;
import service.UserServiceImpl;

@Singleton
@Component(modules = UserServiceModule.class)
public interface UserServiceComponent {
    UserServiceImpl buildUserService();
}
