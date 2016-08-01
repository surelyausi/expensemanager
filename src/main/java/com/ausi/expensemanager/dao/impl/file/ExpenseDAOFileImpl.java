package com.ausi.expensemanager.dao.impl.file;

import com.ausi.expensemanager.dao.db.FileDBConnector;
import com.ausi.expensemanager.dao.interf.CategoryDAO;
import com.ausi.expensemanager.dao.interf.ExpenseDAO;
import com.ausi.expensemanager.domain.Category;
import com.ausi.expensemanager.domain.Expense;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Schurl on 31.07.2016.
 */
public class ExpenseDAOFileImpl implements ExpenseDAO {

    private List<Expense> expenses;

    private CategoryDAO categoryDAO;

    private final String EXPENSES_FIELD = "expenses";
    private final String ID_FIELD = "id";
    private final String CATEGORY_ID_FIELD = "categoryid";
    private final String DESCRIPTION_FIELD = "description";
    private final String DATETIME_FIELD = "datetime";
    private final String AMOUNT_FIELD = "amount";

    private final String DATE_REGEX = "yyyy-MM-dd HH:mm:ss";

    private int nextExpenseID = 1;

    public ExpenseDAOFileImpl() {
        expenses = new ArrayList<>();
        categoryDAO = new CategoryDAOFileImpl();

        parseExpensesFile();
    }

    private void parseExpensesFile() {
        try {
            String jsonString = FileDBConnector.getInstance().readExpenses();
            if (jsonString != null) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

                JSONArray msg = (JSONArray) jsonObject.get(EXPENSES_FIELD);

                Iterator<JSONObject> iterator = msg.iterator();

                while (iterator.hasNext()) {
                    JSONObject expense = iterator.next();
                    int id = Math.toIntExact((Long) expense.get(ID_FIELD));
                    if (id >= nextExpenseID) {
                        nextExpenseID = id + 1;
                    }
                    String description = (String) expense.get(DESCRIPTION_FIELD);
                    double amount = (double) expense.get(AMOUNT_FIELD);
                    String datetime = (String) expense.get(DATETIME_FIELD);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_REGEX);

                    int categoryid = Math.toIntExact((Long) expense.get(CATEGORY_ID_FIELD));

                    Category category = categoryDAO.read(categoryid);

                    Expense expenseEntity = new Expense(id, description, amount, simpleDateFormat.parse(datetime), category, categoryid);
                    expenses.add(expenseEntity);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Expense expense) {
        expense.setId(nextExpenseID);
        nextExpenseID++;
        expenses.add(expense);
        saveExpensesFile();
    }

    private void saveExpensesFile() {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_REGEX);

        JSONArray expenseArray = new JSONArray();
        for (Expense expense : expenses) {
            JSONObject expenseEntry = new JSONObject();
            expenseEntry.put(ID_FIELD, expense.getId());
            expenseEntry.put(DESCRIPTION_FIELD, expense.getDescription());
            expenseEntry.put(AMOUNT_FIELD, expense.getAmount());
            expenseEntry.put(CATEGORY_ID_FIELD, expense.getCategory().getId());
            expenseEntry.put(DATETIME_FIELD, simpleDateFormat.format(expense.getDatetime()));
            expenseArray.add(expenseEntry);
        }
        jsonObject.put(EXPENSES_FIELD, expenseArray);

        FileDBConnector.getInstance().writeExpenses(jsonObject.toJSONString());
    }

    @Override
    public Expense read(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }

        return null;
    }

    @Override
    public List<Expense> readByCategory(Category category) {
        return expenses.parallelStream().filter(expense -> expense.getCategory().getId() == category.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Expense> readAll() {

        return expenses;
    }

    @Override
    public void update(Expense expense) {
        Expense expenseToBeUpdated = read(expense.getId());
        expenseToBeUpdated.setDescription(expense.getDescription());
        expenseToBeUpdated.setAmount(expense.getAmount());
        expenseToBeUpdated.setCategory(expense.getCategory());
        expenseToBeUpdated.setDatetime(expense.getDatetime());
        saveExpensesFile();

    }

    @Override
    public void delete(Expense expense) {
        Expense expenseToBeDeleted = read(expense.getId());
        expenses.remove(expenseToBeDeleted);
        saveExpensesFile();

    }
}
