package controller;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dao.UserDao;
import dao.UserDaoImpl;
import service.UserService;
import service.UserServiceImpl;

@Module
public class UserServiceModule {
    @Provides
    public UserDao provideUserDao(){
        return UserDaoImpl.getInstance();
    }
    @Provides
    public UserServiceImpl provideUserService(){
        return new UserServiceImpl(this.provideUserDao());
    }
}
