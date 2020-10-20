package com.simpleBankingApp.model.dto;

import com.simpleBankingApp.utility.Constants;
import com.simpleBankingApp.utility.ValidatePassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ValidatePassword(passwordField = "password", confirmPasswordField = "confirmPassword")
public class EnableOnlineBankingDto {

    @NotNull
    private Long accountNumber;
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "${validatedValue} is not a valid emailId")
    private String emailId;
    @NotBlank
    @Pattern(regexp = Constants.MOBILE_NUMBER_REGEX, message = "${validatedValue} is not a valid mobile number")
    private String mobileNumber;
    @NotBlank
    @Pattern(regexp = Constants.USER_NAME_REGEX, message = "${validatedValue} is not a valid username")
    private String userName;
    private String password;
    private String confirmPassword;

    public EnableOnlineBankingDto() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
