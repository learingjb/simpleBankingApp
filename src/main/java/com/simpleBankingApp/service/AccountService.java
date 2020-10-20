package com.simpleBankingApp.service;

import com.simpleBankingApp.model.dto.AccountDto;
import com.simpleBankingApp.model.dto.EnableOnlineBankingDto;
import com.simpleBankingApp.model.dto.TransactionDto;

public interface AccountService {
    Long addAccount(AccountDto accountDto);

    Boolean enableOnlineBanking(EnableOnlineBankingDto dto);

    Long getAccountBalance(Long accountNumber);

    Boolean addTransaction(TransactionDto dto);

    Boolean validateLogin(String username, String password);
}
