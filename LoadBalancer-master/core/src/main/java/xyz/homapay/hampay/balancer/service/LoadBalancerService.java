package xyz.homapay.hampay.balancer.service;


import xyz.homapay.hampay.balancer.model.Account;
import xyz.homapay.hampay.balancer.model.Bank;
import xyz.homapay.hampay.common.balancer.model.request.ACHPaymentRequest;
import xyz.homapay.hampay.common.balancer.model.request.PaymentRequest;
import xyz.homapay.hampay.common.balancer.model.request.dto.TransactionLog;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by mushtu on 12/28/15.
 */
public class LoadBalancerService implements Serializable {
    private BankService bankService;

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    public PaymentRequest decideWhichPayment(TransactionLog log) {

        PaymentRequest request = null;

        Bank srcBank = bankService.getBankByCode(log.getSource().getBankCode());
        Bank destBank = bankService.getBankByCode(log.getDestination().getBankCode());


        if (log.getCash() < srcBank.getAchMax()) {
            // ach payment
            // todo consider ach times
            Account hampaySrc = bankService.getHampayAccount(srcBank.getCode());
            Account hampayDest = bankService.getHampayAccount(destBank.getCode());
            request = new ACHPaymentRequest();
            request.setRequestUUID(UUID.randomUUID().toString());
            request.setAmount(log.getCash());

            xyz.homapay.hampay.common.balancer.model.request.dto.Account src = new xyz.homapay.hampay.common.balancer.model.request.dto.Account();
            src.setAccountNumber(hampaySrc.getAccountNumber());
            src.setBankCode(hampaySrc.getBank().getCode());

            xyz.homapay.hampay.common.balancer.model.request.dto.Account dest = new xyz.homapay.hampay.common.balancer.model.request.dto.Account();
            dest.setAccountNumber(hampayDest.getAccountNumber());
            dest.setBankCode(hampayDest.getBank().getCode());

            request.setSourceAccount(src);
            request.setDestinationAccount(dest);
            request.setTransactionId(UUID.randomUUID().toString());


        } else {
            // todo RTGS payment
        }

        return request;
    }

    private Account getHampayAccount(String bankCode) {
        // todo handle hard code

        return null;
    }


    public void criticalBalancing(){
        List<Account> hampayAccounts = bankService.getHampayAccountsList();

        for (Account hampayAccount : hampayAccounts) {
            long minDiff = 0;
            int minDiffIndex = -1;
            long maxDiff = -1;
            int maxDiffIndex = -1;

            for (int i = 0; i < hampayAccounts.size(); i++) {
                Account account = hampayAccounts.get(i);
                long diff = getCurrentCashForHampayAccount(account) - account.getMaxCashThreshold();

                minDiff         = (diff < minDiff)      ? diff  : minDiff;
                minDiffIndex    = (diff < minDiff)      ? i     : maxDiffIndex;

                maxDiff         = (diff > maxDiff)      ? diff  : maxDiff;
                maxDiffIndex    = (diff > maxDiff)      ? i     : maxDiffIndex;
            }

            if (minDiff == 0) break;

            Account hampaySrc = hampayAccounts.get(maxDiffIndex);
            Account hampayDest = hampayAccounts.get(minDiffIndex);

            xyz.homapay.hampay.common.balancer.model.request.dto.Account src = new xyz.homapay.hampay.common.balancer.model.request.dto.Account();
            src.setAccountNumber(hampaySrc.getAccountNumber());
            src.setBankCode(hampaySrc.getBank().getCode());

            xyz.homapay.hampay.common.balancer.model.request.dto.Account dest = new xyz.homapay.hampay.common.balancer.model.request.dto.Account();
            dest.setAccountNumber(hampayDest.getAccountNumber());
            dest.setBankCode(hampayDest.getBank().getCode());

            TransactionLog log = new TransactionLog();

            log.setSource(src);
            log.setDestination(dest);
            log.setCash((maxDiff + minDiff) >  0 ? minDiff : maxDiff);
            log.setTransactionId(UUID.randomUUID().toString());

            transferBetweenHampayAccounts(log);
        }
    }

    public void transferBetweenHampayAccounts(TransactionLog log) {

    }

    public long getCurrentCashForHampayAccount(Account account){
        return 0;
    }

}
