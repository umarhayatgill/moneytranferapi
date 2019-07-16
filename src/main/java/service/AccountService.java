package service;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccount(String accountId) throws NotFoundException;
    BigDecimal getAccountBalance(String accoundId) throws NotFoundException;
    void deleteAccount(String accoundId) throws NotFoundException;
    void makePayment(String transferFrom, String transferTo, String amountToTransfer) throws NotSufficientBalanceException, NotFoundException;
}
