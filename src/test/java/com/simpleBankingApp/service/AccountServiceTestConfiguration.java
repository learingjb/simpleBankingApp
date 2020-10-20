package com.simpleBankingApp.service;

import com.simpleBankingApp.model.database.Account;
import com.simpleBankingApp.model.database.Branch;
import com.simpleBankingApp.model.database.User;
import com.simpleBankingApp.model.dto.AccountDto;
import com.simpleBankingApp.model.dto.EnableOnlineBankingDto;
import com.simpleBankingApp.model.dto.TransactionDto;
import com.simpleBankingApp.service.impl.AccountServiceImpl;
import com.simpleBankingApp.utility.enums.TransactionModes;
import com.simpleBankingApp.utility.enums.TransactionTypes;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class AccountServiceTestConfiguration {
    @Bean
    public AccountServiceImpl accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Account account() {
        return new Account(user(), 123L, 1000L, branch());
    }

    @Bean
    public Branch branch() {
        return new Branch("branch", "place", "district", "state", 676767L, "ifsc");
    }

    @Bean
    public User user() {
        User user = new User();
        user.setName("name");
        user.setPassword(passwordEncoder().encode("password"));
        user.setUserName("username");
        user.setEmailId("abc@cde.com");
        user.setMobileNumber(9999999999L);
        return user;
    }

    @Bean
    public AccountDto accountDto() {
        AccountDto dto = new AccountDto();
        dto.setPinCode("676767");
        dto.setBranchCode("1001");
        dto.setEmailId("adc@cde.com");
        dto.setMobileNumber("9999999999");
        return dto;
    }

    @Bean
    public EnableOnlineBankingDto enableOnlineBankingDto() {
        EnableOnlineBankingDto dto = new EnableOnlineBankingDto();
        dto.setName("name");
        dto.setUserName("username");
        dto.setPassword("password");
        dto.setAccountNumber(12345L);
        dto.setEmailId("abc@cde.com");
        dto.setMobileNumber("9999999999");
        return dto;
    }

    @Bean(name = "credit")
    public TransactionDto transactionDtoCredit(){
       TransactionDto dto = new TransactionDto();
       dto.setAccountNumber(12345L);
       dto.setTransactionType(TransactionTypes.credit);
       dto.setAmount(100L);
       dto.setTransactionMode(TransactionModes.upi);
       dto.setPurpose("purpose");
       return dto;
    }

    @Bean(name = "debit")
    public TransactionDto transactionDtoDebit(){
        TransactionDto dto = new TransactionDto();
        dto.setAccountNumber(12345L);
        dto.setTransactionType(TransactionTypes.debit);
        dto.setAmount(100L);
        dto.setTransactionMode(TransactionModes.pos_transaction);
        dto.setPurpose("purpose");
        return dto;
    }
}
