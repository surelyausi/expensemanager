package com.ausi.expensemanager.dao.impl.file;

import com.ausi.expensemanager.dao.db.FileDBConnector;
import com.ausi.expensemanager.dao.interf.CategoryDAO;
import com.ausi.expensemanager.dao.interf.ExpenseDAO;
import com.ausi.expensemanager.domain.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Schurl on 31.07.2016.
 */
public class CategoryDAOFileImpl implements CategoryDAO {

    private List<Category> categories;

    private final String CATEGORIES_FIELD = "categories";
    private final String ID_FIELD = "id";
    private final String NAME_FIELD = "name";

    private int nextCategoryID = 1;

    private ExpenseDAO expenseDAO;

    public CategoryDAOFileImpl() {
        categories = new ArrayList<>();
        expenseDAO = new ExpenseDAOFileImpl();
        parseCategoryFile();
    }

    private void parseCategoryFile() {
        try {
            String jsonString = FileDBConnector.getInstance().readCategories();
            if (jsonString != null) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

                JSONArray msg = (JSONArray) jsonObject.get(CATEGORIES_FIELD);

                Iterator<JSONObject> iterator = msg.iterator();

                while (iterator.hasNext()) {
                    JSONObject category = iterator.next();
                    int id = Math.toIntExact((Long) category.get(ID_FIELD));
                    if(id >= nextCategoryID){
                        nextCategoryID = id + 1;
                    }
                    String name = (String) category.get(NAME_FIELD);
                    Category categoryEntity = new Category(id, name);
                    categories.add(categoryEntity);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Category category) {
        category.setId(nextCategoryID);
        nextCategoryID++;
        categories.add(category);
        saveCategoryFile();
    }

    private void saveCategoryFile() {
        JSONObject jsonObject = new JSONObject();

        JSONArray categoryArray = new JSONArray();
        for (Category category : categories) {
            JSONObject categoryEntry = new JSONObject();
            categoryEntry.put(ID_FIELD, category.getId());
            categoryEntry.put(NAME_FIELD, category.getName());
            categoryArray.add(categoryEntry);
        }
        jsonObject.put(CATEGORIES_FIELD, categoryArray);

        FileDBConnector.getInstance().writeCategories(jsonObject.toJSONString());
    }

    @Override
    public Category read(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }

        return null;
    }

    @Override
    public List<Category> readAll() {
        return categories;
    }

    @Override
    public void update(Category category) {
        Category categoryToBeUpdated = read(category.getId());
        categoryToBeUpdated.setName(category.getName());
        saveCategoryFile();
    }

    @Override
    public void delete(Category category) {
        Category categoryToBeDeleted = read(category.getId());
        categories.remove(categoryToBeDeleted);
        saveCategoryFile();
    }

    @Override
    public boolean hasEntries(Category category) {
        return expenseDAO.readByCategory(category).size() > 0;
    }
}
