package com.simpleBankingApp.model.dto;

import com.simpleBankingApp.utility.enums.TransactionModes;
import com.simpleBankingApp.utility.enums.TransactionTypes;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransactionDto {

    @NotNull
    private Long accountNumber;
    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("99999999999.00")
    private Long amount;
    private TransactionModes transactionMode;
    private TransactionTypes transactionType;
    private String purpose;

    public TransactionDto() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public TransactionModes getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(TransactionModes transactionMode) {
        this.transactionMode = transactionMode;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypes transactionType) {
        this.transactionType = transactionType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
