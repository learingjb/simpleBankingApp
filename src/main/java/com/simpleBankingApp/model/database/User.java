package com.simpleBankingApp.model.database;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @Column(name = "user_id", updatable = false, unique = true, nullable = false)
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "pin_code", nullable = false)
    private Long pinCode;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id", unique = true)
    private String emailId;

    @Column(name = "mobile_number", unique = true)
    private Long mobileNumber;

    @OneToOne(mappedBy = "user")
    private Account account;

    public User() {
    }

    public User(String name, String place, Long pinCode, String emailId, Long mobileNumber) {
        this.name = name;
        this.place = place;
        this.pinCode = pinCode;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailId() {
        return emailId;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
