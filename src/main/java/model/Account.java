package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountID;
    private String userId;
    private volatile  BigDecimal balance; //volatile to make sure it works correctly in multithreaded context
    private Currency currency;

    private Account(final Account.Builder builder) {
        this.accountID = builder.accountID;
        this.userId = builder.userId;
        this.balance = builder.balance;
        this.currency = builder.currency;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAccountID() {
        return accountID;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public synchronized void setBalance(BigDecimal balance) {
       this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public static class Builder {

        private String accountID;
        private String userId;
        private volatile  BigDecimal balance; //volatile to make sure it works correctly in multithreaded context
        private Currency currency;

        public Builder withAccountID(final String accountID) {
            this.accountID = accountID;
            return this;
        }

        public Builder withUserId(final String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withBalance(final BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder withCurrency(final Currency currency) {
            this.currency = currency;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
