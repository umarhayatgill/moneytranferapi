package dependencyinjection.daggermodules;

import dagger.Module;
import dagger.Provides;
import dao.AccountDao;
import dao.AccountDaoImpl;
import service.AccountServiceImpl;

@Module
public class AccountServiceModule {
    @Provides
    public AccountDao provideAccountDao(){
        return AccountDaoImpl.getInstance();
    }
    @Provides
    public AccountServiceImpl provideAccountService(){
        return new AccountServiceImpl(this.provideAccountDao());
    }
}
