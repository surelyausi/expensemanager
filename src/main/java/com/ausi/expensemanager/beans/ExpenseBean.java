package com.ausi.expensemanager.beans;

import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.domain.Expense;
import com.ausi.expensemanager.service.CategoryService;
import com.ausi.expensemanager.service.ExpenseService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Schurl on 31.07.2016.
 */
@ManagedBean(name = "expenseBean")
@SessionScoped
public class ExpenseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Expense> expenses = null;
    private ExpenseService expenseService;
    private CategoryService categoryService;

    public String newExpenseDescription = "";
    public String newExpenseAmount = "";
    public String newExpenseCategoryID = "";

    public Date newExpenseDateAndTime = null;

    public ExpenseBean() {
        expenseService = new ExpenseService();
        categoryService = new CategoryService();
    }

    @PostConstruct
    public void init() {
        loadExpenses();
        setDateNow();
    }

    private void setDateNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date now = new Date(System.currentTimeMillis());
        newExpenseDateAndTime = now;
    }

    private void loadExpenses() {
        expenses = expenseService.readAll();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getNewExpenseDescription() {
        return newExpenseDescription;
    }

    public void setNewExpenseDescription(String newExpenseDescription) {
        this.newExpenseDescription = newExpenseDescription;
    }

    public String getNewExpenseAmount() {
        return newExpenseAmount;
    }

    public void setNewExpenseAmount(String newExpenseAmount) {
        this.newExpenseAmount = newExpenseAmount;
    }

    public String getNewExpenseCategoryID() {
        return newExpenseCategoryID;
    }

    public void setNewExpenseCategoryID(String newExpenseCategoryID) {
        this.newExpenseCategoryID = newExpenseCategoryID;
    }

    public Date getNewExpenseDateAndTime() {
        return newExpenseDateAndTime;
    }

    public void setNewExpenseDateAndTime(Date newExpenseDateAndTime) {
        this.newExpenseDateAndTime = newExpenseDateAndTime;
    }

    private void reloadPage() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            setDateNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNew() {
        if (newExpenseAmount != null && !newExpenseAmount.isEmpty() && newExpenseDescription != null && !newExpenseDescription.isEmpty() && newExpenseDateAndTime != null && newExpenseCategoryID != null && !newExpenseCategoryID.isEmpty()) {
                Expense expense = new Expense(1, newExpenseDescription, Double.valueOf(newExpenseAmount), newExpenseDateAndTime, categoryService.read(Integer.valueOf(newExpenseCategoryID)),Integer.valueOf(newExpenseCategoryID));
                expenseService.create(expense);
        }
        loadExpenses();
        reloadPage();
    }


}
