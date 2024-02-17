package org.odyssey.cms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    @OneToOne(mappedBy = "userId")
    private User UserID;
    private Double balance;
    private LocalDate openDate;
    private String status;

    public Account(Integer accountId, User userID, Double balance, LocalDate openDate, String status) {
        this.accountId = accountId;
        UserID = userID;
        this.balance = balance;
        this.openDate = openDate;
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public User getUserID() {
        return UserID;
    }

    public void setUserID(User userID) {
        UserID = userID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", UserID=" + UserID +
                ", balance=" + balance +
                ", openDate=" + openDate +
                ", status='" + status + '\'' +
                '}';
    }
}
