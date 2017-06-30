package xyz.homapay.hampay.balancer.dao.impl;

import com.mongodb.DBRef;
import xyz.homapay.hampay.balancer.dao.BankDao;
import xyz.homapay.hampay.balancer.data.DbConnection;
import xyz.homapay.hampay.balancer.model.Account;
import xyz.homapay.hampay.balancer.model.Bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohsen ZareZardeyni
 *         date 1/2/16
 *         Mail To m.zare@homapay.com
 */

public class BankDaoImpl implements BankDao {
    @Override
    public Bank getBankByCode(String code)
    {

        Bank bank = null;
        try {
            bank = DbConnection.datastore().find(Bank.class).field("code").equal(code).get();
        } catch (IOException e) {
            e.printStackTrace();
            //todo handling exception
        }
        return bank;

    }

    @Override
    public Account getHampayAccount(String bankCode)
    {

        Account account = null;
        Bank bank = getBankByCode(bankCode);
        if(bank != null) {

            try {
                account = DbConnection.datastore().find(Account.class).field("bank").equal(new DBRef(Bank.class.getName(),bank.getId())).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return account;
    }

    @Override
    public List<Bank> getAllBanks() {
        try {
            return DbConnection.datastore().find(Bank.class).asList();
        } catch (IOException ignored) {

        }
        return new ArrayList<>();
    }

    @Override
    public List<Account> getHampayAccountsList() {
        List<Bank> banks = getAllBanks();
        List<Account> accounts = new ArrayList<>();

        for (Bank bank : banks) {
            accounts.add(getHampayAccount(bank.getCode()));
        }

        return accounts;

    }
}
