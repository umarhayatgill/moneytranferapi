package dao;

import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public interface AccountDao {
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId) throws NotFoundException;
    BigDecimal getAccountBalance(String accountId) throws NotFoundException;
    void createAccount(Account accountId) throws AlreadyExistException;
    void deleteAccount(String accountId) throws NotFoundException;
    void withdrawMoney(String accountId, BigDecimal amountToWithdraw) throws NotFoundException, NotSufficientBalanceException;
    void depositMoney(String accountId, BigDecimal amountToDeposit) throws NotFoundException;
    void makePayment(String transferFrom, String transferTo, BigDecimal amountToTransfer) throws NotSufficientBalanceException, NotFoundException;
}
