package xyz.homapay.hampay.balancer.service;

import xyz.homapay.hampay.balancer.dao.BankDao;
import xyz.homapay.hampay.balancer.dao.impl.BankDaoImpl;
import xyz.homapay.hampay.balancer.model.Account;
import xyz.homapay.hampay.balancer.model.Bank;

import java.util.List;

/**
 * @author Mohsen ZareZardeyni
 *         date 1/2/16
 *         Mail To m.zare@homapay.com
 */

public class BankService {
    private BankDao bankDao = new BankDaoImpl();

    public Bank getBankByCode(String code){
        return bankDao.getBankByCode(code);
    }

    public Account getHampayAccount(String bankCode){
        return bankDao.getHampayAccount(bankCode);
    }

    public List<Bank> getAllBanks(){
        return bankDao.getAllBanks();
    }

    public List<Account> getHampayAccountsList(){
        return bankDao.getHampayAccountsList();
    }

}
