package dao;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public Account getAccount(String accountId) throws NotFoundException;
    public List<Account> getAllAccounts();
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalanceException;
}
