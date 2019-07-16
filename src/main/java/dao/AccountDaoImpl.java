package dao;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;
import model.User;

import java.math.BigDecimal;
import java.util.*;

public class AccountDaoImpl implements AccountDao {
    Account account;
    Map<String, Account> accountsDatabase = new HashMap<>();
    //in memory database
    public AccountDaoImpl(){
        account = new Account();
        account.setAccountID("1");
        account.setUser(User.builder().withId("1").withFirstName("foo").withLastName("bar")
        .withEmail("foo@bar.com").build());
        account.setBalance(BigDecimal.valueOf(200));
        account.setCurrency(Currency.getInstance("EUR"));
        accountsDatabase.put(account.getAccountID(), account);
        account = new Account();
        account.setAccountID("2");
        account.setUser(User.builder().withId("2").withFirstName("anotherfoo").withLastName("anotherbar")
                .withEmail("anotherfoo@anotherbar.com").build());
        account.setBalance(BigDecimal.valueOf(20));
        account.setCurrency(Currency.getInstance("EUR"));
        accountsDatabase.put(account.getAccountID(), account);
    }

    private static AccountDaoImpl accountDaoImpl = null;
    //we can place synchronized inside method as well with double lock checking mechanism
    // to increase performance but i have placed it at method level for sake of simplicity of this task.
    public static synchronized AccountDaoImpl getInstance(){
        if(accountDaoImpl==null){
            accountDaoImpl = new AccountDaoImpl();
        }
        return accountDaoImpl;
    }

    @Override
    public Account getAccount(String accountId) throws NotFoundException {
        return accountsDatabase.values().stream().filter(account -> account.getAccountID().equals(accountId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Account number %s not found"));
    }

    @Override
    public BigDecimal getAccountBalance(String accountId) throws NotFoundException {
        Optional<Account> account = accountsDatabase.values().stream().filter(acc -> acc.getAccountID().equals(accountId)).findFirst();
        if(account.isPresent()){
            return account.get().getBalance();
        }
        else {
           throw new NotFoundException("Account number %s not found");
        }
    }

    @Override
    public void deleteAccount(String accountId) throws NotFoundException {
        accountsDatabase.remove(accountId);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accountsDatabase.values();
    }

    @Override
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalanceException {
        if(transferFrom.getBalance().compareTo(amountToTransfer) < 0){
            throw new NotSufficientBalanceException("Balance is not sufficient to make transaction");
        }
        else{
            transferFrom.setBalance(transferFrom.getBalance().subtract(amountToTransfer));
            transferTo.setBalance(transferTo.getBalance().add(amountToTransfer));
        }
    }
}
