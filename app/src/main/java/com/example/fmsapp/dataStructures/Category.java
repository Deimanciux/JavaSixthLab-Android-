package com.example.fmsapp.dataStructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    private int id;
    private String name = "none";
    private String description;
    private List<User> responsiblePerson = new ArrayList<>();
    private Category parentCategory = null;
    private List<Category> subCategories = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private FinanceManagementSystem financeManagementSystem;

    public Category() {
    }

    public Category(
            int id,
            String name,
            String description,
            List<User> responsiblePerson,
            Category parentCategory,
            List<Category> subCategories,
            List<Income> incomes,
            List<Expense> expenses,
            FinanceManagementSystem financeManagementSystem
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.responsiblePerson = responsiblePerson;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.incomes = incomes;
        this.expenses = expenses;
        this.financeManagementSystem = financeManagementSystem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(List<User> responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public FinanceManagementSystem getFinanceManagementSystem() {
        return financeManagementSystem;
    }

    public void setFinanceManagementSystem(FinanceManagementSystem financeManagementSystem) {
        this.financeManagementSystem = financeManagementSystem;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toPrint() {
        return "id = " + id + '\n' +
                "name = " + name + '\n' +
                "description = " + description + '\n' +
                "parentCategory = " + parentCategory + '\n';
    }
}