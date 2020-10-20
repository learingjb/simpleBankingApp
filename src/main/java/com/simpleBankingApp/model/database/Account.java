package com.simpleBankingApp.model.database;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSequenceGenerator")
    @Column(name = "account_number", updatable = false, nullable = false, unique = true)
    @SequenceGenerator(name = "accountSequenceGenerator", sequenceName = "account_sequence", initialValue = 1, allocationSize = 1)
    private Long accountNumber;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "atm_card_number", unique = true)
    private Long atmCardNumber;

    @Column(name = "account_balance", nullable = false)
    private Long accountBalance;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "branch_code", referencedColumnName = "branch_code", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;

    public Account() {
    }

    public Account(User user, Long atmCardNumber, Long accountBalance, Branch branch) {
        this.user = user;
        this.atmCardNumber = atmCardNumber;
        this.accountBalance = accountBalance;
        this.branch = branch;
        user.setAccount(this);
        branch.addAccount(this);
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public User getUser() {
        return user;
    }

    public Long getAtmCardNumber() {
        return atmCardNumber;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public Branch getBranch() {
        return branch;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setAtmCardNumber(Long atmCardNumber) {
        this.atmCardNumber = atmCardNumber;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addTransaction(Transaction transaction) {
        transactions = transactions == null ? new HashSet<>() : transactions;
        transactions.add(transaction);
    }
}
