package com.example.fmsapp.dataStructures;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanceManagementSystem implements Serializable {
    private int id;
    private String company;
    private LocalDate systemCreated;
    private String systemVersion;
    private List<User> users = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public FinanceManagementSystem() {
    }

    public FinanceManagementSystem(int id, String company, LocalDate systemCreated, String systemVersion, List<User> users, List<Category> categories) {
        this.id = id;
        this.company = company;
        this.systemCreated = systemCreated;
        this.systemVersion = systemVersion;
        this.users = users;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getSystemCreated() {
        return systemCreated;
    }

    public void setSystemCreated(LocalDate systemCreated) {
        this.systemCreated = systemCreated;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return company + " " + systemVersion;
    }
}
