import dependencyinjection.daggercomponents.AccountServiceComponent;
import dependencyinjection.daggercomponents.DaggerAccountServiceComponent;
import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import exception.SameAccountException;
import model.Account;
import org.junit.Assert;
import org.junit.Test;
import service.AccountService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

public class AccountServiceIntegrationTest {
    AccountServiceComponent accountServiceComponent = DaggerAccountServiceComponent.create();
    AccountService accountService = accountServiceComponent.buildAccountService();

    @Test
    public void getAllAccounts(){
        Collection<Account> accounts = accountService.getAllAccounts();
        Assert.assertEquals(accounts.size(),2);
    }

    @Test
    public void createNewAccount() throws AlreadyExistException, NotFoundException {
        try {
            Account account = Account.builder().withAccountID("3").withUserId("2")
                    .withCurrency(Currency.getInstance("EUR")).withBalance(BigDecimal.valueOf(50)).build();
            accountService.createAccount(account);
            Collection<Account> accounts = accountService.getAllAccounts();
            Assert.assertEquals(accounts.size(), 3);
        }finally {
            accountService.deleteAccount("3");
        }
    }

    @Test
    public void deleteAccount() throws NotFoundException, AlreadyExistException {
        try {
            accountService.deleteAccount("2");
            Collection<Account> accounts = accountService.getAllAccounts();
            Assert.assertEquals(accounts.size(), 1);
        }finally {
            Account account = Account.builder().withAccountID("2").withUserId("2")
                    .withBalance(BigDecimal.valueOf(20)).withCurrency(Currency.getInstance("EUR")).build();
            accountService.createAccount(account);
        }
    }

    @Test
    public void withdrawMoneyFromAccount() throws NotSufficientBalanceException, NotFoundException {
        try {
            BigDecimal amountBeforeWithdrawl = accountService.getAccountBalance("2");
            accountService.withdrawMoney("2","10");
            Collection<Account> accounts = accountService.getAllAccounts();
            Assert.assertEquals(amountBeforeWithdrawl.subtract(BigDecimal.valueOf(10)), accountService.getAccountBalance("2"));
        }finally {
            accountService.depositMoney("2","10");
        }
    }

    @Test
    public void depositMoneyFromAccount() throws NotSufficientBalanceException, NotFoundException {
        try {
            BigDecimal amountBeforeDeposit = accountService.getAccountBalance("2");
            accountService.depositMoney("2","10");
            Assert.assertEquals(amountBeforeDeposit.add(BigDecimal.valueOf(10)), accountService.getAccountBalance("2"));
        }finally {
            accountService.withdrawMoney("2","10");
        }
    }

    @Test
    public void transferMoneyBetweenAccount() throws NotSufficientBalanceException, NotFoundException, SameAccountException {
        try {
            BigDecimal amountFromAccountBeforeDeposit = accountService.getAccountBalance("1");
            BigDecimal amountToAccountBeforeDeposit = accountService.getAccountBalance("2");
            accountService.makePayment("1","2","10");
            Assert.assertEquals(amountFromAccountBeforeDeposit.subtract(BigDecimal.valueOf(10)), accountService.getAccountBalance("1"));
            Assert.assertEquals(amountToAccountBeforeDeposit.add(BigDecimal.valueOf(10)), accountService.getAccountBalance("2"));
        }finally {
            accountService.makePayment("2","1","0");
        }
    }
}
