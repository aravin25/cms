package org.odyssey.cms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class CreditCard {
    @Id
    private Integer cardNumber;
    @OneToOne(mappedBy = "accountId")
    private Account account;

    private LocalDate expireDate;
    private Integer cvv;
    private Integer amount;
    private String activationStatus;

    @OneToMany(mappedBy = "TransactionID")
    private List<Transaction> transactionList;

    @OneToMany(mappedBy = "invoiceId")
    private List<Invoice> invoiceList;

    public CreditCard(Integer cardNumber, Account account, LocalDate expireDate, Integer cvv, Integer amount, String activationStatus) {
        this.cardNumber = cardNumber;
        this.account = account;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.amount = amount;
        this.activationStatus = activationStatus;
    }

    public CreditCard(Integer cardNumber, Account account, LocalDate expireDate, Integer cvv, Integer amount, String activationStatus, List<Transaction> transactionList, List<Invoice> invoiceList) {
        this.cardNumber = cardNumber;
        this.account = account;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.amount = amount;
        this.activationStatus = activationStatus;
        this.transactionList = transactionList;
        this.invoiceList = invoiceList;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber=" + cardNumber +
                ", account=" + account +
                ", expireDate=" + expireDate +
                ", cvv=" + cvv +
                ", amount=" + amount +
                ", activationStatus='" + activationStatus + '\'' +
                ", transactionList=" + transactionList +
                ", invoiceList=" + invoiceList +
                '}';
    }
}
