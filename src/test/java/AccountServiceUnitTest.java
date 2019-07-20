import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dao.AccountDao;
import exception.NotSufficientBalanceException;
import exception.SameAccountException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import dao.UserDao;
import exception.AlreadyExistException;
import exception.NotFoundException;
import service.AccountServiceImpl;
import service.UserServiceImpl;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceUnitTest {
    AccountDao accountDao = mock(AccountDao.class);
    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void callDaoGetUsersWhenServiceGetUsersIsCalled(){
        given(accountDao.getAllAccounts()).willReturn(null);
        accountServiceImpl.getAllAccounts();
        verify(accountDao,times(1)).getAllAccounts();
    }
    @Test
    public void callDaoGetUserByIdWhenServiceGetUserByIdIsCalled() throws NotFoundException {
        given(accountDao.getAccount("1")).willReturn(null);
        accountServiceImpl.getAccount("1");
        verify(accountDao,times(1)).getAccount("1");
    }
    @Test
    public void callDaoCreateUserWhenServiceCreateUserByIdIsCalled() throws AlreadyExistException {
        accountServiceImpl.createAccount(null);
        verify(accountDao,times(1)).createAccount(null);
    }

    @Test
    public void callDaoDepositMoneyWhenServiceDepositMoneyIsCalled() throws AlreadyExistException, NotFoundException, NotSufficientBalanceException {
        accountServiceImpl.depositMoney(null, null);
        verify(accountDao,times(1)).depositMoney(null,null);
    }

    @Test
    public void callDaoWithdrawMoneyWhenServiceWithdrawMoneyIsCalled() throws AlreadyExistException, NotFoundException, NotSufficientBalanceException {
        accountServiceImpl.depositMoney(null, null);
        verify(accountDao,times(1)).depositMoney(null,null);
    }

    @Test
    public void callDaoMakePaymentWhenServiceMakePaymentIsCalled() throws AlreadyExistException, NotFoundException, NotSufficientBalanceException, SameAccountException {
        accountServiceImpl.makePayment("1", "2","50");
        verify(accountDao,times(1)).makePayment("1","2", BigDecimal.valueOf(50));
    }
}
