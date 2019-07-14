package service;

import dao.AccountDao;
import dao.AccountDaoImpl;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = AccountDaoImpl.getInstance();
    @Override
    public Account getAccount(String accountId) throws NotFoundException {
        return accountDao.getAccount(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public void makePayment(String transferFrom, String transferTo, String amountToTransfer) throws NotSufficientBalanceException, NotFoundException {
        Account transferFromAccount = accountDao.getAccount(transferFrom);
        Account transferToAccount = accountDao.getAccount(transferTo);
        BigDecimal amountToTransferInDecimal = BigDecimal.valueOf(Long.valueOf(amountToTransfer));
        accountDao.makePayment(transferFromAccount, transferToAccount, amountToTransferInDecimal);
    }
}
