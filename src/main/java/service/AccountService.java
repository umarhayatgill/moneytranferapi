package service;

import exception.NotSufficientBalance;
import model.Account;

import java.math.BigDecimal;

public interface AccountService {
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalance;
}
