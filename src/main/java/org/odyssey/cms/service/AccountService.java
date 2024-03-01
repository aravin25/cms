package org.odyssey.cms.service;


import org.odyssey.cms.entity.Account;
import org.odyssey.cms.exception.AccountException;

import java.util.List;

public interface AccountService {
    //create
    Account createAccount(Account newAccount, String type) throws AccountException;

    //read
    Account getAccountById(Integer accountId) throws AccountException;
    List<Account> getAllAccounts();

    //update
    Account updateAccount(Account account)throws AccountException;

    //delete
    Account deleteAccountById(Integer id)throws AccountException;

}
