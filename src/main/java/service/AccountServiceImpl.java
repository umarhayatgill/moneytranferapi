package service;

import dao.AccountDao;
import dao.AccountDaoImpl;
import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = AccountDaoImpl.getInstance();
    @Override
    public Account getAccount(final String accountId) throws NotFoundException {
        return accountDao.getAccount(accountId);
    }

    @Override
    public BigDecimal getAccountBalance(final String accoundId) throws NotFoundException {
        return accountDao.getAccountBalance(accoundId);
    }

    @Override
    public void deleteAccount(final String accoundId) throws NotFoundException {
        accountDao.deleteAccount(accoundId);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public void makePayment(final String transferFrom, final String transferTo, final String amountToTransfer) throws NotSufficientBalanceException, NotFoundException {
        Account transferFromAccount = this.getAccount(transferFrom);
        Account transferToAccount = this.getAccount(transferTo);
        BigDecimal amountToTransferInDecimal = BigDecimal.valueOf(Long.valueOf(amountToTransfer));
        accountDao.makePayment(transferFromAccount, transferToAccount, amountToTransferInDecimal);
    }

    @Override
    public void createAccount(final Account account) throws AlreadyExistException {
        accountDao.createAccount(account);
    }
}
