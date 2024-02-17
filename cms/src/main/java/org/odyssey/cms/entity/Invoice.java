package org.odyssey.cms.entity;


import jakarta.persistence.*;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoiceId;

    @OneToOne(mappedBy = "transactionID")
    private Transaction trancation;

    @ManyToOne
    private CreditCard userTransactionDetail;

    public Invoice(Integer invoiceId, Transaction trancation, CreditCard userTransactionDetail) {
        this.invoiceId = invoiceId;
        this.trancation = trancation;
        this.userTransactionDetail = userTransactionDetail;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Transaction getTrancation() {
        return trancation;
    }

    public void setTrancation(Transaction trancation) {
        this.trancation = trancation;
    }

    public CreditCard getUserTransactionDetail() {
        return userTransactionDetail;
    }

    public void setUserTransactionDetail(CreditCard userTransactionDetail) {
        this.userTransactionDetail = userTransactionDetail;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", trancation=" + trancation +
                ", userTransactionDetail=" + userTransactionDetail +
                '}';
    }
}
