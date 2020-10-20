package com.simpleBankingApp.service;

import com.simpleBankingApp.model.database.Account;
import com.simpleBankingApp.model.database.Branch;
import com.simpleBankingApp.model.database.Transaction;
import com.simpleBankingApp.model.database.User;
import com.simpleBankingApp.model.dto.AccountDto;
import com.simpleBankingApp.model.dto.EnableOnlineBankingDto;
import com.simpleBankingApp.model.dto.TransactionDto;
import com.simpleBankingApp.repository.AccountRepository;
import com.simpleBankingApp.repository.BranchRepository;
import com.simpleBankingApp.repository.TransactionRepository;
import com.simpleBankingApp.repository.UserRepository;
import com.simpleBankingApp.utility.enums.TransactionTypes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(AccountServiceTestConfiguration.class)
public class AccountServiceTest {

    @MockBean
    private BranchRepository branchRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Account account;

    @Autowired
    private User user;

    @Autowired
    private AccountDto accountDto;

    @Autowired
    private Branch branch;

    @Autowired
    private EnableOnlineBankingDto enableOnlineBankingDto;

    @Autowired
    @Qualifier("credit")
    private TransactionDto transactionDtoCredit;

    @Autowired
    @Qualifier("debit")
    private TransactionDto transactionDtoDebit;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @After
    public void after() {
        account.setAccountBalance(1000L);
    }

    @Test
    public void testAccountBalanceMethodWithValidAccountNumber() {
        Mockito.when(accountRepository.existsById(123L)).thenReturn(true);
        Mockito.when(accountRepository.getOne(123L)).thenReturn(account);
        Long balance = accountService.getAccountBalance(123L);
        Assert.assertNotNull(balance);
        Assert.assertEquals(1000L, balance.longValue());
    }

    @Test
    public void testAccountBalanceMethodWithInValidAccountNumber() {
        Mockito.when(accountRepository.existsById(123L)).thenReturn(false);
        Long balance = accountService.getAccountBalance(123L);
        Assert.assertNull(balance);
    }

    @Test
    public void testValidateLoginMethodWithValidLoginDetails() {
        Mockito.when(userRepository.findOneByUserName("username")).thenReturn(user);
        Assert.assertTrue(accountService.validateLogin("username","password"));
    }

    @Test
    public void testValidateLoginMethodWithInValidUserName() {
        Mockito.when(userRepository.findOneByUserName("username")).thenReturn(null);
        Assert.assertFalse(accountService.validateLogin("username","password"));
    }

    @Test
    public void testValidateLoginMethodWithPasswordMisMatch() {
        Mockito.when(userRepository.findOneByUserName("username")).thenReturn(user);
        Assert.assertFalse(accountService.validateLogin("username","password1"));
    }

    @Test
    public void testAddAccountWithInvalidBranchCode() {
        Mockito.when(branchRepository.existsById(1001L)).thenReturn(false);
        Assert.assertNull(accountService.addAccount(accountDto));
        Mockito.verify(accountRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testAddAccountWithInvalidEmailId() {
        Mockito.when(userRepository.existsUserByEmailIdIgnoreCase("adc@cde.com")).thenReturn(true);
        Assert.assertNull(accountService.addAccount(accountDto));
        Mockito.verify(accountRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testAddAccountWithInvalidMobileNumber() {
        Mockito.when(userRepository.existsUserByMobileNumber(9999999999L)).thenReturn(true);
        Assert.assertNull(accountService.addAccount(accountDto));
        Mockito.verify(accountRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testAddAccountWithValidDetails() {
        Mockito.when(branchRepository.existsById(1001L)).thenReturn(true);
        Mockito.when(userRepository.existsUserByEmailIdIgnoreCase("adc@cde.com")).thenReturn(false);
        Mockito.when(userRepository.existsUserByMobileNumber(9999999999L)).thenReturn(false);
        Mockito.when(branchRepository.getOne(1001L)).thenReturn(branch);
        accountService.addAccount(accountDto);
        Mockito.verify(accountRepository, Mockito.times(1)).save(accountArgumentCaptor.capture());
        Account dao = accountArgumentCaptor.getValue();
        Assert.assertEquals(9999999999L, dao.getUser().getMobileNumber().longValue());
        Assert.assertEquals("adc@cde.com", dao.getUser().getEmailId());
    }

    @Test
    public void testEnableOnlineBankingMethodWithInvalidUserName() {
        Mockito.when(userRepository.existsUserByUserName("username")).thenReturn(true);
        Assert.assertFalse(accountService.enableOnlineBanking(enableOnlineBankingDto));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testEnableOnlineBankingMethodWithInvalidAccountNumber() {
        Mockito.when(userRepository.existsUserByUserName("username")).thenReturn(false);
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(false);
        Assert.assertFalse(accountService.enableOnlineBanking(enableOnlineBankingDto));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testEnableOnlineBankingMethodWithValidDetails() {
        Mockito.when(userRepository.existsUserByUserName("username")).thenReturn(false);
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(true);
        Mockito.when(accountRepository.getOne(12345L)).thenReturn(account);
        Assert.assertTrue(accountService.enableOnlineBanking(enableOnlineBankingDto));
        Mockito.verify(userRepository, Mockito.times(1)).save(userArgumentCaptor.capture());
        User dao = userArgumentCaptor.getValue();
        Assert.assertEquals("username", dao.getUserName());
        Assert.assertTrue(passwordEncoder.matches("password", user.getPassword()));
    }

    @Test
    public void testAddTransactionMethodWithInValidAccountNumber() {
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(false);
        Assert.assertFalse(accountService.addTransaction(transactionDtoCredit));
        Mockito.verify(transactionRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testAddTransactionMethodWithValidDetailsCredit() {
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(true);
        Mockito.when(accountRepository.getOne(12345L)).thenReturn(account);
        accountService.addTransaction(transactionDtoCredit);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionArgumentCaptor.capture());
        Transaction dao = transactionArgumentCaptor.getValue();
        Assert.assertEquals(transactionDtoCredit.getAmount(), dao.getAmount());
        Assert.assertEquals(transactionDtoCredit.getPurpose(), dao.getTransactionDetail());
        Assert.assertEquals(TransactionTypes.credit, dao.getTransactionType());
        Assert.assertEquals(1100L, dao.getBalance().longValue());
    }

    @Test
    public void testAddTransactionMethodWithValidDetailsDebit() {
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(true);
        Mockito.when(accountRepository.getOne(12345L)).thenReturn(account);
        accountService.addTransaction(transactionDtoDebit);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionArgumentCaptor.capture());
        Transaction dao = transactionArgumentCaptor.getValue();
        Assert.assertEquals(transactionDtoDebit.getAmount(), dao.getAmount());
        Assert.assertEquals(transactionDtoDebit.getPurpose(), dao.getTransactionDetail());
        Assert.assertEquals(TransactionTypes.debit, dao.getTransactionType());
        Assert.assertEquals(900L, dao.getBalance().longValue());
    }

    @Test
    public void testAddTransactionMethodWithValidDetailsDebitWithInsufficientBalance() {
        Mockito.when(accountRepository.existsById(12345L)).thenReturn(true);
        account.setAccountBalance(10L);
        Mockito.when(accountRepository.getOne(12345L)).thenReturn(account);
        Assert.assertFalse(accountService.addTransaction(transactionDtoDebit));
        Mockito.verify(transactionRepository, Mockito.never()).save(Mockito.any());
    }


}
