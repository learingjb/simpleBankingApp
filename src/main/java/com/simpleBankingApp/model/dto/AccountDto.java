package com.simpleBankingApp.model.dto;

import com.simpleBankingApp.utility.Constants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AccountDto {

    @NotBlank
    private String name;
    @NotBlank
    private String place;
    @NotBlank
    @Pattern(regexp = Constants.PIN_CODE_REGEX, message = "${validatedValue} is not a valid pin code")
    private String pinCode;
    @NotBlank
    @Email(message = "${validatedValue} is not a valid emailId")
    private String emailId;
    @NotBlank
    @Pattern(regexp = Constants.MOBILE_NUMBER_REGEX, message = "${validatedValue} is not a valid mobile number")
    private String mobileNumber;
    @NotBlank
    private String branchCode;

    public AccountDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
