package com.ausi.expensemanager.dao.interf;

import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.domain.Expense;

import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public interface ExpenseDAO {
    void create(Expense expense);
    Expense read(int id);
    List<Expense> readByCategory(Category category);
    List<Expense> readAll();
    void update(Expense expense);
    void delete(Expense expense);
}
