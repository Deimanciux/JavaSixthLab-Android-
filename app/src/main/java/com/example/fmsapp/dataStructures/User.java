package com.example.fmsapp.dataStructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String loginName;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String type;
    private String surname;
    private String contactPersonName;
    private String contactPersonSurname;
    protected List<Category> categories = new ArrayList<>();
    private FinanceManagementSystem financeManagementSystem;

    public User() {
    }

    public User(
            int id,
            String loginName,
            String name,
            String password,
            String email,
            String phoneNumber,
            String type,
            String surname,
            String contactPersonName,
            String contactPersonSurname,
            List<Category> categories,
            FinanceManagementSystem financeManagementSystem
    ) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.surname = surname;
        this.contactPersonName = contactPersonName;
        this.contactPersonSurname = contactPersonSurname;
        this.categories = categories;
        this.financeManagementSystem = financeManagementSystem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonSurname() {
        return contactPersonSurname;
    }

    public void setContactPersonSurname(String contactPersonSurname) {
        this.contactPersonSurname = contactPersonSurname;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public FinanceManagementSystem getFinanceManagementSystem() {
        return financeManagementSystem;
    }

    public void setFinanceManagementSystem(FinanceManagementSystem financeManagementSystem) {
        this.financeManagementSystem = financeManagementSystem;
    }

    @Override
    public String toString() {
        return "User{" +
                "loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
