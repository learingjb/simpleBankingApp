package com.simpleBankingApp.model.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "branch",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"branch_name", "place", "district", "state", "pin_code"}))
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branchSequenceGenerator")
    @Column(name = "branch_code", updatable = false, nullable = false, unique = true)
    @SequenceGenerator(name = "branchSequenceGenerator", sequenceName = "branch_sequence", initialValue = 1, allocationSize = 1)
    private Long branchCode;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "pin_code", nullable = false)
    private Long pinCode;

    @Column(name = "ifsc_code", nullable = false, unique = true)
    private String ifscCode;

    @OneToMany(mappedBy = "branch")
    private Set<Account> accounts;

    public Branch() {
    }

    public Branch(String branchName, String place, String district, String state, Long pinCode, String ifscCode) {
        this.branchName = branchName;
        this.place = place;
        this.district = district;
        this.state = state;
        this.pinCode = pinCode;
        this.ifscCode = ifscCode;
    }

    public Long getBranchCode() {
        return branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getPlace() {
        return place;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts = accounts == null ? new HashSet<>() : accounts;
        accounts.add(account);
    }
}
