package org.odyssey.cms.service;


import org.odyssey.cms.entity.Account;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;

import java.util.List;

public interface AccountService {
    //create
    Account createAccount(Account newAccount) throws AccountException, NotificationException;

    //read
    Account getAccountById(Integer accountId) throws AccountException;
    List<Account> getAllAccounts();

    //update
    Account updateAccount(Account account)throws AccountException,NotificationException;

    //delete
    Account deleteAccountById(Integer id)throws AccountException,NotificationException;

}
