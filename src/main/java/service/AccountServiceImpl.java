package service;

import dao.AccountDao;
import dao.AccountDaoImpl;
import dao.UserDao;
import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import exception.SameAccountException;
import model.Account;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    @Inject
    public AccountServiceImpl(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    @Override
    public Account getAccount(final String accountId) throws NotFoundException {
        return accountDao.getAccount(accountId);
    }

    @Override
    public BigDecimal getAccountBalance(final String accountId) throws NotFoundException {
        return accountDao.getAccountBalance(accountId);
    }

    @Override
    public void deleteAccount(final String accountId) throws NotFoundException {
        accountDao.deleteAccount(accountId);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public void createAccount(final Account account) throws AlreadyExistException {
        accountDao.createAccount(account);
    }

    @Override
    public void withdrawMoney(final String accountId, final BigDecimal amountToWithdraw) throws NotFoundException, NotSufficientBalanceException {
        accountDao.withdrawMoney(accountId, amountToWithdraw);
    }

    @Override
    public void depositMoney(final String accountId, final BigDecimal amountToWithdraw) throws NotFoundException, NotSufficientBalanceException {
        accountDao.depositMoney(accountId, amountToWithdraw);
    }

    @Override
    public void makePayment(final String transferFrom, final String transferTo, final String amountToTransfer) throws NotSufficientBalanceException, NotFoundException, SameAccountException {
        Objects.requireNonNull(transferFrom);
        Objects.requireNonNull(transferTo);
        Objects.requireNonNull(amountToTransfer);
        if(transferFrom.equals(transferTo)){
            throw new SameAccountException("Amount can not be transferred between same accounts");
        }
        BigDecimal amountToTransferInDecimal = BigDecimal.valueOf(Long.valueOf(amountToTransfer));
        accountDao.makePayment(transferFrom, transferTo, amountToTransferInDecimal);
    }
}
