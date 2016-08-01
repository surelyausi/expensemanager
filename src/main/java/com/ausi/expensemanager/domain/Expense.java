package com.ausi.expensemanager.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Schurl on 31.07.2016.
 */
@Entity
@Table(name="EXPENSES", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Expense {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", nullable=false, unique=true, length=11)
    private int id;
    @Column(name="DESCRIPTION", nullable = true, length = 2000)
    private String description;
    @Column(name="AMOUNT", nullable = true)
    private double amount;
    @Column(name="DATE_TIME", nullable = true, length = 2000)
    private Date datetime;
    @Column(name="CATEGORY_ID", nullable=false, unique=true, length=11)
    private int categoryId;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
    private Category category;

    public Expense(int id, String description, double amount, Date datetime, Category category, int categoryId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.datetime = datetime;
        this.category = category;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
