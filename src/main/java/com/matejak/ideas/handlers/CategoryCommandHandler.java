package com.matejak.ideas.handlers;

import com.matejak.ideas.dao.CategoryDao;
import com.matejak.ideas.input.UserInputCommand;
import com.matejak.ideas.model.Category;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(CategoryCommandHandler.class.getName());

    private static final String COMMAND_NAME = "category";

    private CategoryDao categoryDao;

    public CategoryCommandHandler() {
        categoryDao = new CategoryDao();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("Action can't be null.");
        }

        switch (command.getAction()) {
            case LIST:
                LOG.log(Level.INFO, "List of categories...");

                if (!command.getParams().isEmpty()) {
                    throw new IllegalArgumentException("Category list doesn't support any additional params.");
                }

                List<Category> categories = categoryDao.findAll();
                categories.forEach(System.out::println);
                break;

            case ADD:
                LOG.log(Level.INFO, "Add category");

                if (command.getParams().size() != 1) {
                    throw new IllegalArgumentException("Wrong command format. Check `help` for more information.");
                }

                String categoryName = command.getParams().get(0);
                categoryDao.add(new Category(categoryName));
                break;

            default:
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
        }

    }
}
