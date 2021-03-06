package com.ausi.expensemanager.beans;

/**
 * Created by Schurl on 31.07.2016.
 */

import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.service.CategoryService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="categoryBean")
@ViewScoped
public class CategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Category> categories = null;
    private CategoryService categoryService;

    public String newCategoryName = "";

    public CategoryBean() {
        categoryService = new CategoryService();
    }

    @PostConstruct
    public void init() {
        loadCategories();
    }

    private void loadCategories() {
        categories = categoryService.readAll();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getNewCategoryName() {
        return newCategoryName;
    }

    public void setNewCategoryName(String newCategoryName) {
        this.newCategoryName = newCategoryName;
    }

    public void createNew() {
        if(newCategoryName != null && !newCategoryName.isEmpty()) {
            //System.out.println(newCategoryName);
            Category category = new Category(1, newCategoryName);
            categoryService.create(category);
        }
        loadCategories();
        reloadPage();
    }

    private void reloadPage() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Category category){
        if(!categoryService.hasEntries(category)) {
            categoryService.delete(category);
            loadCategories();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!","You can't delete Categories that aren't empty!"));
        }
    }

}
