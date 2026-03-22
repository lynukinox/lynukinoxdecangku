package com.repair.pojo;

public class User {
    private Long id;
    private String account;
    private String password;
    private String role;
    private String dormNo;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDormNo() { return dormNo; }
    public void setDormNo(String dormNo) { this.dormNo = dormNo; }
}