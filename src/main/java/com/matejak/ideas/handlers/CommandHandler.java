package com.matejak.ideas.handlers;

import com.matejak.ideas.input.UserInputCommand;

import java.io.IOException;

public interface CommandHandler {
    void handle(UserInputCommand command) throws IOException;

    boolean supports(String name);
}
