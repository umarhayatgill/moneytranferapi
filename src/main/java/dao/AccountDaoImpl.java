package dao;

import exception.NotSufficientBalance;
import model.Account;

import java.math.BigDecimal;

public class AccountDaoImpl implements AccountDao {
    private static AccountDaoImpl accountDaoImpl = null;

    //we can place synchronized inside method as well with double lock checking mechanism
    // to increase performance but i have placed it at method level for sake of simplicity of this task.
    public static synchronized AccountDaoImpl getInstance(){
        if(accountDaoImpl==null){
            accountDaoImpl = new AccountDaoImpl();
        }
        return accountDaoImpl;
    }

    @Override
    public void makePayment(Account transferFrom, Account transferTo, BigDecimal amountToTransfer) throws NotSufficientBalance {
        if(transferFrom.getBalance().compareTo(amountToTransfer) < 0){
            throw new NotSufficientBalance("Balance is not sufficient to make transaction");
        }
        else{
            transferFrom.setBalance(transferFrom.getBalance().subtract(amountToTransfer));
            transferTo.setBalance(transferTo.getBalance().add(amountToTransfer));
        }
    }
}
