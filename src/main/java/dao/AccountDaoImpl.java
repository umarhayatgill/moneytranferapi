package dao;

import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;
import model.User;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDaoImpl implements AccountDao {
    Account account;
    Map<String, Account> accountsDatabase = new ConcurrentHashMap<>();
    //in memory database
    public AccountDaoImpl(){
        account = Account.builder().withAccountID("1").withUserId("1")
                .withBalance(BigDecimal.valueOf(200)).withCurrency(Currency.getInstance("EUR")).build();
        accountsDatabase.put(account.getAccountID(), account);
        account = Account.builder().withAccountID("2").withUserId("2")
                .withBalance(BigDecimal.valueOf(20)).withCurrency(Currency.getInstance("EUR")).build();
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
                .orElseThrow(() -> new NotFoundException("Account number not found"));
    }

    @Override
    public BigDecimal getAccountBalance(String accountId) throws NotFoundException {
        Optional<Account> account = accountsDatabase.values().stream().filter(acc -> acc.getAccountID().equals(accountId)).findFirst();
        if(account.isPresent()){
            return account.get().getBalance();
        }
        else {
           throw new NotFoundException("Account number not found");
        }
    }

    @Override
    public void createAccount(final Account account) throws AlreadyExistException {
        if(accountsDatabase.containsKey(account.getAccountID())){
            throw new AlreadyExistException("Account number already exist");
        }
        else{
            accountsDatabase.put(account.getAccountID(),account);
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
    public void withdrawMoney(final String accountId, final BigDecimal amountToWithdraw) throws NotFoundException, NotSufficientBalanceException {
        Optional<Account> account = accountsDatabase.values().stream().filter(acc -> acc.getAccountID().equals(accountId)).findFirst();
        if(account.isPresent()){
            if(account.get().getBalance().compareTo(amountToWithdraw) < 0){
                throw new NotSufficientBalanceException("Balance is not sufficient for withdrawl");
            }
            account.get().setBalance(account.get().getBalance().subtract(amountToWithdraw));
            accountsDatabase.put(accountId, account.get());
        }
        else {
            throw new NotFoundException("Account number not found");
        }
    }

    @Override
    public void depositMoney(final String accountId, final BigDecimal amountToDeposit) throws NotFoundException {
        Optional<Account> account = accountsDatabase.values().stream().filter(acc -> acc.getAccountID().equals(accountId)).findFirst();
        if(account.isPresent()){
            account.get().setBalance(account.get().getBalance().add(amountToDeposit));
            accountsDatabase.put(accountId, account.get());
        }
        else {
            throw new NotFoundException("Account number not found");
        }
    }

    @Override
    public void makePayment(String transferFrom, String transferTo, BigDecimal amountToTransfer) throws NotSufficientBalanceException, NotFoundException {
        synchronized (accountsDatabase.get(transferFrom)) { //lock the account rows you are going to make transactions on to avoid concurrency problems.
            synchronized (accountsDatabase.get(transferTo)) {
                Account transferFromAccount = this.getAccount(transferFrom);
                Account transferToAccount = this.getAccount(transferTo);
                if (transferFromAccount.getBalance().compareTo(amountToTransfer) < 0) {
                    throw new NotSufficientBalanceException("Balance is not sufficient to make transaction");
                } else {
                    transferFromAccount.setBalance(transferFromAccount.getBalance().subtract(amountToTransfer));
                    transferToAccount.setBalance(transferToAccount.getBalance().add(amountToTransfer));
                }
            }
        }
    }

}
