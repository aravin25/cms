package org.odyssey.cms.service;


import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.LastPayment;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;

import java.util.List;

public interface AccountService {
    //create
    Account createAccount(Account newAccount, String type) throws AccountException, CreditCardException;

    //read
    Account getAccountById(Integer accountId) throws AccountException;
    List<Account> getAllAccounts();

    //update
    Account updateAccount(Account account)throws AccountException;

    //delete
    Account deleteAccountById(Integer id)throws AccountException;

    Boolean createLasteDate(LastPayment lastPayment)throws AccountException;

    LastPayment getLastDate(Integer accountId)throws AccountException;

}
