package com.simpleBankingApp.utility;

public class Constants {
    public static final String MOBILE_NUMBER_REGEX = "(0/91)?[6-9][0-9]{9}";
    public static final String PIN_CODE_REGEX = "^[1-9][0-9]{5}$";
    public static final String USER_NAME_REGEX = "^[A-Za-z]\\w{5,29}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
}
