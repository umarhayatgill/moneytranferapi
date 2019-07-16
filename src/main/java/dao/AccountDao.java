package dao;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public interface AccountDao {
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId) throws NotFoundException;
    BigDecimal getAccountBalance(String accountId) throws NotFoundException;
    void deleteAccount(String accountId) throws NotFoundException;
    void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalanceException;
}
