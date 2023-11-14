package com.matejak.ideas.handlers;

import com.matejak.ideas.dao.CategoryDao;
import com.matejak.ideas.dao.QuestionDao;
import com.matejak.ideas.input.UserInputCommand;
import com.matejak.ideas.model.Category;
import com.matejak.ideas.model.Question;
import com.matejak.ideas.model.Answer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnswerCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(AnswerCommandHandler.class.getName());

    private static final String COMMAND_NAME = "answer";

    private QuestionDao questionDao;
    private CategoryDao categoryDao;

    public AnswerCommandHandler() {
        questionDao = new QuestionDao();
        categoryDao = new CategoryDao();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) throws IOException {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("Action can't be null.");
        }

        switch (command.getAction()) {
            case LIST:
                LOG.log(Level.INFO, "List of answers...");

                if (command.getParams().size() != 1) {
                    throw new IllegalArgumentException("Answer list require one and only one param.");
                }

                String questionName = command.getParams().get(0);
                Question question = questionDao.findOne(questionName)
                        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionName));

                displayQuestion(question);
                break;

            case ADD:
                LOG.log(Level.INFO, "Add answer");

                if (command.getParams().size() != 2) {
                    throw new IllegalArgumentException("Wrong command format. Check `help` for more information.");
                }

                questionName = command.getParams().get(0);
                String answerName = command.getParams().get(1);

                question = questionDao.findOne(questionName)
                        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionName));
                questionDao.addAnswer(question, new Answer(answerName));

                break;

            default:
                throw new IllegalArgumentException(String.format("Unknown action: %s from command %s",
                        command.getAction(), command.getCommand()));
        }

    }

    private void displayQuestion(Question question) {
        System.out.println(question.getName());
        question.getAnswers().forEach(System.out::println);
    }
}
