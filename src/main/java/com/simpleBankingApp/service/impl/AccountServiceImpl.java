package com.simpleBankingApp.service.impl;

import com.simpleBankingApp.model.database.Account;
import com.simpleBankingApp.model.database.Transaction;
import com.simpleBankingApp.model.database.User;
import com.simpleBankingApp.model.dto.AccountDto;
import com.simpleBankingApp.model.dto.EnableOnlineBankingDto;
import com.simpleBankingApp.model.dto.TransactionDto;
import com.simpleBankingApp.repository.AccountRepository;
import com.simpleBankingApp.repository.BranchRepository;
import com.simpleBankingApp.repository.TransactionRepository;
import com.simpleBankingApp.repository.UserRepository;
import com.simpleBankingApp.service.AccountService;
import com.simpleBankingApp.utility.enums.TransactionTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long addAccount(AccountDto dto) {
        if (branchRepository.existsById(Long.parseLong(dto.getBranchCode()))) {
            if (!(userRepository.existsUserByEmailIdIgnoreCase(dto.getEmailId()) ||
                    userRepository.existsUserByMobileNumber(Long.parseLong(dto.getMobileNumber())))) {
                Account account = new Account(
                        new User(dto.getName(), dto.getPlace(), Long.parseLong(dto.getPinCode()), dto.getEmailId(), Long.parseLong(dto.getMobileNumber())),
                        null, 0L, branchRepository.getOne(Long.parseLong(dto.getBranchCode())));
                accountRepository.save(account);
                if (account.getAccountNumber() != null) {
                    logger.info("account created");
                    return account.getAccountNumber();
                }
            } else {
                logger.error("duplicate emailId/mobile number");
            }
        } else {
            logger.error("invalid branch code");
        }
        return null;
    }

    @Override
    public Boolean enableOnlineBanking(EnableOnlineBankingDto dto) {
        if (!userRepository.existsUserByUserName(dto.getUserName())) {
            if (accountRepository.existsById(dto.getAccountNumber())) {
                User user = accountRepository.getOne(dto.getAccountNumber()).getUser();
                if (user.getName().equalsIgnoreCase(dto.getName()) && user.getEmailId().equalsIgnoreCase(dto.getEmailId()) &&
                        user.getMobileNumber().equals(Long.parseLong(dto.getMobileNumber()))) {
                    user.setUserName(dto.getUserName());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    userRepository.save(user);
                    logger.info("online banking enabled");
                    return true;
                } else {
                    logger.error("mismatch in user details");
                }
            } else {
                logger.error("invalid account number");
            }
        } else {
            logger.error("username already used");
        }
        return false;
    }

    @Override
    public Boolean validateLogin(String username, String password) {

        User dao = userRepository.findOneByUserName(username);
        if (dao != null && dao.getPassword() != null) {
            if (passwordEncoder.matches(password, dao.getPassword())) {
                logger.info("valid user login");
                return true;
            } else {
                logger.error("password mismatch");
            }
        } else {
            logger.error("invalid username");
        }
        return false;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long getAccountBalance(Long accountNumber) {
        if (accountRepository.existsById(accountNumber)) {
            logger.info("account balance returned");
            return accountRepository.getOne(accountNumber).getAccountBalance();
        }
        logger.info("Invalid account details, unable to fetch balance");
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Boolean addTransaction(TransactionDto dto) {
        if (accountRepository.existsById(dto.getAccountNumber())) {
            Account account = accountRepository.getOne(dto.getAccountNumber());
            Long balance = account.getAccountBalance();
            if (dto.getTransactionType().equals(TransactionTypes.credit)) {
                balance = balance + dto.getAmount();
            } else if (dto.getTransactionType().equals(TransactionTypes.debit) && dto.getAmount() < balance) {
                balance = balance - dto.getAmount();
            } else {
                logger.error("Insufficient balance");
                return false;
            }
            account.setAccountBalance(balance);
            Transaction transaction = new Transaction(account, dto.getTransactionMode(), dto.getTransactionType(),
                    dto.getAmount(), balance, new Date().getTime(), dto.getPurpose());
            transactionRepository.save(transaction);
            if (transaction.getTransactionId() != null) {
                logger.info("Transaction successful");
                return true;
            } else {
                logger.error("Transaction Failure");
            }

        } else {
            logger.error("Invalid account number");
        }
        return false;
    }
}
