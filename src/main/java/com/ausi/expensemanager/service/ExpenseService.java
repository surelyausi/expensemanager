package com.ausi.expensemanager.service;

import com.ausi.expensemanager.dao.impl.file.ExpenseDAOFileImpl;
import com.ausi.expensemanager.dao.impl.hibernate.ExpenseDAOHibernateImpl;
import com.ausi.expensemanager.dao.interf.ExpenseDAO;
import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.domain.Expense;

import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public class ExpenseService {

    private ExpenseDAO expenseDAO;

    public ExpenseService(){
        expenseDAO = new ExpenseDAOHibernateImpl();
    }

    public void create(Expense expense) {
        expenseDAO.create(expense);
    }

    public Expense read(int id) {
        return expenseDAO.read(id);
    }

    public List<Expense> readByCategory(Category category) {
        return expenseDAO.readByCategory(category);
    }

    public List<Expense> readAll() {
        return expenseDAO.readAll();
    }

    public void update(Expense expense) {
        expenseDAO.update(expense);
    }

    public void delete(Expense expense) {
        expenseDAO.delete(expense);
    }
}
