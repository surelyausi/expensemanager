package com.ausi.expensemanager.dao.impl.hibernate;

import com.ausi.expensemanager.dao.interf.CategoryDAO;
import com.ausi.expensemanager.domain.Category;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public class CategoryDAOHibernateImpl implements CategoryDAO {

    private final SessionFactory factory;

    public CategoryDAOHibernateImpl() {
        //creating configuration object
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        factory = cfg.buildSessionFactory();
    }

    @Override
    public void create(Category category) {
        Session session = factory.openSession();
        try {
            session.save(category);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category read(int id) {
        Session session = factory.openSession();
        Category category =  (Category) session.get(Category.class, id);
        return category;
    }

    @Override
    public List<Category> readAll() {
        Session session = factory.openSession();
        return session.createCriteria(Category.class).list();
    }

    @Override
    public void update(Category category) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(category);
        tx.commit();
        session.close();

    }

    @Override
    public void delete(Category category) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(category);

        transaction.commit();
        session.close();
    }


    @Override
    public boolean hasEntries(Category category) {

        Session session = factory.openSession();
        Query query = session.createQuery("select count(*) from Expense where categoryId = :catid");
        query.setParameter("catid", category.getId());
        int count = ((Long)query.uniqueResult()).intValue();
        return count > 0;
    }
}
