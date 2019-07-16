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
    public BigDecimal getAccountBalance(String accoundId) throws NotFoundException {
        return accountDao.getAccountBalance(accoundId);
    }

    @Override
    public void deleteAccount(String accoundId) throws NotFoundException {
        accountDao.deleteAccount(accoundId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public void makePayment(String transferFrom, String transferTo, String amountToTransfer) throws NotSufficientBalanceException, NotFoundException {
        Account transferFromAccount = this.getAccount(transferFrom);
        Account transferToAccount = this.getAccount(transferTo);
        BigDecimal amountToTransferInDecimal = BigDecimal.valueOf(Long.valueOf(amountToTransfer));
        accountDao.makePayment(transferFromAccount, transferToAccount, amountToTransferInDecimal);
    }
}
