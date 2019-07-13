package service;

import exception.NotSufficientBalance;
import model.Account;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    @Override
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalance {

    }
}
