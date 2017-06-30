package xyz.homapay.hampay.balancer.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * @author Siavash Mahmoudpour
 */

@Entity(value = "accounts", noClassnameStored = true)
public class Account extends BaseEntity {

    private String accountNumber;

    private long minCashThreshold;
    private long maxCashThreshold;


    @Reference
    private Bank bank;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountNumber != null ? !accountNumber.equals(account.accountNumber) : account.accountNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    public long getMinCashThreshold() {
        return minCashThreshold;
    }

    public long getMaxCashThreshold() {
        return maxCashThreshold;
    }
}
