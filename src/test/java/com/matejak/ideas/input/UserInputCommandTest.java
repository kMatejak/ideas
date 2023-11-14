package com.matejak.ideas.input;

import com.matejak.ideas.Action;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserInputCommandTest {

    @Test
    void shouldBuildCorrectUserInputCommand() {
        // given
        String input = "category add CategoryName";

        // when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        // then
        assertEquals("category", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertLinesMatch(List.of("CategoryName"), userInputCommand.getParams());

    }

    @Test
    void shouldBuildCorrectUserInputCommandWithMultipleParams() {
        // given
        String input = "command add param1 param2 param3";

        // when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        // then
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertLinesMatch(List.of("param1", "param2", "param3"), userInputCommand.getParams());

    }

    @Test
    void shouldBuildCorrectUserInputCommandWithoutParams() {
        // given
        String input = "command add";

        // when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        // then
        assertEquals("command", userInputCommand.getCommand());
        assertEquals(Action.ADD, userInputCommand.getAction());
        assertEquals(0, userInputCommand.getParams().size());
    }
}