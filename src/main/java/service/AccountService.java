package service;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.util.List;

public interface AccountService {
    public Account getAccount(String accountId) throws NotFoundException;
    public List<Account> getAllAccounts();
    public void makePayment(String transferFrom, String transferTo, String amountToTransfer) throws NotSufficientBalanceException, NotFoundException;
}
