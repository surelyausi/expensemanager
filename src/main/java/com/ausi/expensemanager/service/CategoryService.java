package com.ausi.expensemanager.service;

import com.ausi.expensemanager.dao.impl.file.CategoryDAOFileImpl;
import com.ausi.expensemanager.dao.impl.hibernate.CategoryDAOHibernateImpl;
import com.ausi.expensemanager.dao.interf.CategoryDAO;
import com.ausi.expensemanager.domain.Category;

import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = new CategoryDAOHibernateImpl();
    }

    public void create(Category category) {
        categoryDAO.create(category);
    }

    public Category read(int id) {
        return categoryDAO.read(id);
    }

    public List<Category> readAll() {
        return categoryDAO.readAll();
    }

    public void update(Category category) {
        categoryDAO.update(category);
    }

    public void delete(Category category) {
        categoryDAO.delete(category);
    }
}
