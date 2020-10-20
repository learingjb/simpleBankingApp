package com.simpleBankingApp.controllers;

import com.simpleBankingApp.model.dto.AccountDto;
import com.simpleBankingApp.model.dto.EnableOnlineBankingDto;
import com.simpleBankingApp.model.dto.TransactionDto;
import com.simpleBankingApp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("account")
@Api("Controller for user related operations")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    @RequestMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "add new account", consumes = "user details along with branch code",
            produces = "account number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account created"),
            @ApiResponse(code = 400, message = "Input validation failed")
    })
    public ResponseEntity<Long> addAccount(@Valid @RequestBody AccountDto accountDto) {
        logger.info("request to create new account");
        Long accountNumber = accountService.addAccount(accountDto);
        if (accountNumber != null) {
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountNumber);
        }
        return ResponseEntity.badRequest().cacheControl(CacheControl.noCache()).build();
    }

    @RequestMapping(value = "/enable/online-banking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "enable online banking")
    public ResponseEntity<String> enableOnlineBanking(@Valid @RequestBody EnableOnlineBankingDto dto) {
        logger.info("request to enable online banking");
        if (accountService.enableOnlineBanking(dto)) {
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("enabled");
        }
        return ResponseEntity.badRequest().cacheControl(CacheControl.noCache()).build();
    }

    @RequestMapping(value = "/validate-login/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "validate user login")
    public ResponseEntity<Boolean> validateLogin(@PathVariable("username") @NotBlank String username, @PathVariable("password") @NotBlank String password) {
        logger.info("request for validate user login");
        return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountService.validateLogin(username, password));
    }

    @RequestMapping(value = "/balance/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "fetch user account balance")
    public ResponseEntity<Long> getAccountBalance(@PathVariable("accountNumber") @NotBlank String accountNumber) {
        Long accountBalance = accountService.getAccountBalance(Long.parseLong(accountNumber));
        if (accountBalance != null) {
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountBalance);
        }
        return ResponseEntity.badRequest().cacheControl(CacheControl.noCache()).build();
    }

    @RequestMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ApiOperation(value = "transaction request")
    public ResponseEntity<String> addTransaction(@Valid @RequestBody TransactionDto dto) {
        logger.info("new transaction request");
        if (accountService.addTransaction(dto)) {
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("added");
        }
        return ResponseEntity.badRequest().cacheControl(CacheControl.noCache()).build();
    }


}
