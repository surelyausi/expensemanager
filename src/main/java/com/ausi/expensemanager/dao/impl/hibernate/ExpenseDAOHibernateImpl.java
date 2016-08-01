package com.ausi.expensemanager.dao.impl.hibernate;

import com.ausi.expensemanager.dao.interf.ExpenseDAO;
import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.domain.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public class ExpenseDAOHibernateImpl implements ExpenseDAO{

    private final SessionFactory factory;

    public ExpenseDAOHibernateImpl() {
        //creating configuration object
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        factory = cfg.buildSessionFactory();
    }

    @Override
    public void create(Expense expense) {
        Session session = factory.openSession();
        try {
            session.save(expense);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Expense read(int id) {
        Session session = factory.openSession();
        Expense expense =  session.get(Expense.class, id);
        return expense;
    }

    @Override
    public List<Expense> readAll() {
        Session session = factory.openSession();
        return session.createCriteria(Expense.class).list();
    }

    @Override
    public void update(Expense expense) {
        Session session = factory.openSession();
        session.update(expense);

    }

    @Override
    public void delete(Expense expense) {
        Session session = factory.openSession();
        session.delete(expense);
    }

    @Override
    public List<Expense> readByCategory(Category category) {
        //TODO: READ BY CATEGORY EVERYWHERE
        return null;
    }

}
