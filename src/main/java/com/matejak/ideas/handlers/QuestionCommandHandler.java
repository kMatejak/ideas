package com.matejak.ideas.handlers;

import com.matejak.ideas.dao.CategoryDao;
import com.matejak.ideas.dao.QuestionDao;
import com.matejak.ideas.input.UserInputCommand;
import com.matejak.ideas.model.Category;
import com.matejak.ideas.model.Question;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(QuestionCommandHandler.class.getName());

    private static final String COMMAND_NAME = "question";

    private QuestionDao questionDao;
    private CategoryDao categoryDao;

    public QuestionCommandHandler() {
        questionDao = new QuestionDao();
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
                LOG.log(Level.INFO, "List of questions...");

                if (!command.getParams().isEmpty()) {
                    throw new IllegalArgumentException("Question list doesn't support any additional params.");
                }

                List<Question> questions = questionDao.findAll();
                questions.forEach(System.out::println);
                break;

            case ADD:
                LOG.log(Level.INFO, "Add question");

                if (command.getParams().size() < 2) {
                    throw new IllegalArgumentException("Wrong command format. Check `help` for more information.");
                }

                String categoryName = command.getParams().get(0);
                String questionName = command.getParamsText();

                Category category = categoryDao.findOne(categoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

                questionDao.add(new Question(questionName, category));
                break;

            default:
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
        }

    }
}
