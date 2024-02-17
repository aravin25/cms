package org.odyssey.cms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    @OneToOne(mappedBy = "userId")
    private User user;
    private Double balance;
    private LocalDate openDate;
    private String status;

    public Account(Integer accountId, User user, Double balance, LocalDate openDate, String status) {
        this.accountId = accountId;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", UserID=" + user +
                ", balance=" + balance +
                ", openDate=" + openDate +
                ", status='" + status + '\'' +
                '}';
    }
}
