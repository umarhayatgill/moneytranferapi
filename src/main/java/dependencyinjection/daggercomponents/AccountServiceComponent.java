package dependencyinjection.daggercomponents;

import dagger.Component;
import dependencyinjection.daggermodules.AccountServiceModule;
import service.AccountServiceImpl;

@Component(modules = AccountServiceModule.class)
public interface AccountServiceComponent {
    AccountServiceImpl buildUserService();
}
