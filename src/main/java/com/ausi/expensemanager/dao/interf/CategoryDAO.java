package com.ausi.expensemanager.dao.interf;

import com.ausi.expensemanager.domain.Category;

import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public interface CategoryDAO {
    void create(Category category);
    Category read(int id);
    List<Category> readAll();
    void update(Category category);
    void delete(Category category);
    boolean hasEntries(Category category);
}
