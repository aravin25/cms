package org.odyssey.cms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionID;
    @ManyToOne
    private CreditCard creditCard;
    private Double amount;
    private LocalDate transactionDate;
    @OneToOne(mappedBy = "userId")
    private User merchantID;

    public Transaction(Integer transactionID, CreditCard creditCard, Double amount, LocalDate transactionDate, User merchantID) {
        this.transactionID = transactionID;
        this.creditCard = creditCard;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.merchantID = merchantID;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public User getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(User merchantID) {
        this.merchantID = merchantID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", creditCard=" + creditCard +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", merchantID=" + merchantID +
                '}';
    }
}
