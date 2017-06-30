package xyz.homapay.hampay.balancer.dao;


import xyz.homapay.hampay.balancer.model.Account;
import xyz.homapay.hampay.balancer.model.Bank;

import java.util.List;

/**
 * Created by mushtu on 12/28/15.
 */
public interface BankDao {
    Bank getBankByCode(String code);

    Account getHampayAccount(String bankCode);

    List<Bank> getAllBanks();

    List<Account> getHampayAccountsList();
}
