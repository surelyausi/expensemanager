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
    public String newExpenseDateTime = "";

    public ExpenseBean() {
        expenseService = new ExpenseService();
        categoryService = new CategoryService();
    }

    @PostConstruct
    public void init() {
        loadExpenses();
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

    public String getNewExpenseDateTime() {
        return newExpenseDateTime;
    }

    public void setNewExpenseDateTime(String newExpenseDateTime) {
        this.newExpenseDateTime = newExpenseDateTime;
    }

    public String getNewExpenseCategoryID() {
        return newExpenseCategoryID;
    }

    public void setNewExpenseCategoryID(String newExpenseCategoryID) {
        this.newExpenseCategoryID = newExpenseCategoryID;
    }


    private void reloadPage() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNew() {
        System.out.println(newExpenseAmount);
        System.out.println(getNewExpenseDescription());
        System.out.println(getNewExpenseDateTime());
        System.out.println(newExpenseCategoryID);
        if (newExpenseAmount != null && !newExpenseAmount.isEmpty() && newExpenseDescription != null && !newExpenseDescription.isEmpty() && newExpenseDateTime != null && !newExpenseDateTime.isEmpty() && newExpenseCategoryID != null && !newExpenseCategoryID.isEmpty()) {

                //TODO: TAKE REAL DATETIME
                //DateFormat simpleDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                Expense expense = new Expense(1, newExpenseDescription, Double.valueOf(newExpenseAmount), new Date(System.currentTimeMillis()), categoryService.read(Integer.valueOf(newExpenseCategoryID)),Integer.valueOf(newExpenseCategoryID));
                expenseService.create(expense);
        }
        loadExpenses();
        reloadPage();
    }


}
