package com.simpleBankingApp.model.database;

import com.simpleBankingApp.utility.enums.TransactionModes;
import com.simpleBankingApp.utility.enums.TransactionTypes;

import javax.persistence.*;

@Entity
@Table(name = "transaction",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"account_number", "transaction_timestamp", "amount", "balance"}))
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionSequenceGenerator")
    @Column(name = "transaction_id", updatable = false, nullable = false, unique = true)
    @SequenceGenerator(name = "transactionSequenceGenerator", sequenceName = "transaction_sequence", initialValue = 1, allocationSize = 1)
    private Long transactionId;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number", nullable = false)
    private Account account;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_mode", updatable = false, nullable = false)
    private TransactionModes transactionMode;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type", updatable = false, nullable = false)
    private TransactionTypes transactionType;

    @Column(name = "amount", updatable = false, nullable = false)
    private Long amount;

    @Column(name = "balance", updatable = false, nullable = false)
    private Long balance;

    @Column(name = "transaction_timestamp", updatable = false, nullable = false)
    private Long transactionTimestamp;

    @Column(name = "transaction_detail", updatable = false)
    private String transactionDetail;

    public Transaction() {
    }

    public Transaction(Account account, TransactionModes transactionMode, TransactionTypes transactionType, Long amount, Long balance, Long transactionTimestamp, String transactionDetail) {
        this.account = account;
        this.transactionMode = transactionMode;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.transactionTimestamp = transactionTimestamp;
        this.transactionDetail = transactionDetail;
        account.addTransaction(this);
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionModes getTransactionMode() {
        return transactionMode;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public String getTransactionDetail() {
        return transactionDetail;
    }
}
