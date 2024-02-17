package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Admin {
    List<Account> allAccount;
    List<Transaction> allTransaction;
    List<CreditCard> allCreditCard;

    public Admin(List<Account> allAccount, List<Transaction> allTransaction, List<CreditCard> allCreditCard) {
        this.allAccount = allAccount;
        this.allTransaction = allTransaction;
        this.allCreditCard = allCreditCard;
    }

    public List<Account> getAllAccount() {
        return allAccount;
    }

    public void setAllAccount(List<Account> allAccount) {
        this.allAccount = allAccount;
    }

    public List<Transaction> getAllTransaction() {
        return allTransaction;
    }

    public void setAllTransaction(List<Transaction> allTransaction) {
        this.allTransaction = allTransaction;
    }

    public List<CreditCard> getAllCreditCard() {
        return allCreditCard;
    }

    public void setAllCreditCard(List<CreditCard> allCreditCard) {
        this.allCreditCard = allCreditCard;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "allAccount=" + allAccount +
                ", allTransaction=" + allTransaction +
                ", allCreditCard=" + allCreditCard +
                '}';
    }
}
