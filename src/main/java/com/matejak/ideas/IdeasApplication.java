package com.matejak.ideas;

import com.matejak.ideas.handlers.*;
import com.matejak.ideas.input.UserInputCommand;
import com.matejak.ideas.input.UserInputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdeasApplication {

    private static Logger LOG = Logger.getLogger(IdeasApplication.class.getName());

    public static void main(String[] args) {
        new IdeasApplication().start();
    }

    private void start() {

        //
        // - TODO support for whitespaces into user's entering data (different possible approaches)
        // - TODO database for storing data
        // - TODO more generic DAO (related to adding a database) and/or framework for data handling
        // - TODO full CRUD implementation
        // - TODO lambda expressions for complex loops
        //


        LOG.log(Level.INFO, "Start app...");

        boolean applicationLoop = true;

        UserInputManager userInputManager = new UserInputManager();
        List<CommandHandler> handlers = new ArrayList<>();

        handlers.add(new HelpCommandHandler());
        handlers.add(new QuitCommandHandler());
        handlers.add(new CategoryCommandHandler());
        handlers.add(new QuestionCommandHandler());
        handlers.add(new AnswerCommandHandler());

        while (applicationLoop) {
            try {
                UserInputCommand userInputCommand = userInputManager.nextCommand();
                LOG.log(Level.INFO, userInputCommand.toString());

                Optional<CommandHandler> currentHandler = Optional.empty();

                for (CommandHandler handler : handlers) {
                    if (handler.supports(userInputCommand.getCommand())) {
                        currentHandler = Optional.of(handler);
                        break;
                    }
                }
                currentHandler
                        .orElseThrow(() -> new IllegalArgumentException("Unknown handler: " + userInputCommand.getCommand()))
                        .handle(userInputCommand);

            } catch (QuitIdeasApplicationException e) {
                LOG.log(Level.INFO, "Quit...");
                applicationLoop = false;

            } catch (IllegalArgumentException e) {
                LOG.log(Level.WARNING, "Validation exception: " + e.getMessage());
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Unknown error", e);
            }
        }
    }
}
