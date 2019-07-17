package service;

import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public interface AccountService {
    Collection<Account> getAllAccounts();
    Account getAccount(String accountId) throws NotFoundException;
    BigDecimal getAccountBalance(String accoundId) throws NotFoundException;
    void deleteAccount(String accoundId) throws NotFoundException;
    void makePayment(String transferFrom, String transferTo, String amountToTransfer) throws NotSufficientBalanceException, NotFoundException;
    void createAccount(Account account) throws AlreadyExistException;
}
