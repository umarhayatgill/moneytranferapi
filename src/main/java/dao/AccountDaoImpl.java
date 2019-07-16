package dao;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    Account account;
    List<Account> list = new ArrayList<>();
    //in memory database
    public AccountDaoImpl(){
        account = new Account();
        account.setAccountID("123");
        account.setBalance(BigDecimal.valueOf(200));
        account.setCurrency(Currency.getInstance("EUR"));
        list.add(account);
        account = new Account();
        account.setAccountID("234");
        account.setBalance(BigDecimal.valueOf(20));
        account.setCurrency(Currency.getInstance("EUR"));
        list.add(account);
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
        return list.stream().filter(account -> account.getAccountID().equals(accountId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Account number %s not found"));
    }

    @Override
    public List<Account> getAllAccounts() {
        return list;
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
