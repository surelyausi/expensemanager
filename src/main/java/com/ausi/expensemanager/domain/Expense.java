package com.ausi.expensemanager.domain;

import java.util.Date;

/**
 * Created by Schurl on 31.07.2016.
 */
public class Expense {
    private int id;
    private String description;
    private double amount;
    private Date datetime;
    private Category category;

    public Expense(int id, String description, double amount, Date datetime, Category category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
        this.category = category;
    }

    public Expense() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        return id == expense.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", datetime=" + datetime +
                ", category=" + category +
                '}';
    }
}
