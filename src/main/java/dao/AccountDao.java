package dao;

import exception.NotSufficientBalance;
import model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalance;
}
