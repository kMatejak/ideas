package com.matejak.ideas.input;

import com.matejak.ideas.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInputCommand {

    private String command;

    private Action action;

    private List<String> params;

    public UserInputCommand(String line) {
        if (line != null) {
            String[] array = line.split("\\s");
            if (array.length > 0) {
                command = array[0];
            }
            if (array.length > 1) {
                action = Action.of(array[1]);
            }

            params = new ArrayList<>();
            for (int i = 2; i < array.length; i++) {
                params.add(array[i]);
                
            }
        }

    }

    public String getCommand() {
        return command;
    }

    public Action getAction() {
        return action;
    }

    public List<String> getParams() {
        return params;
    }

    public String getParamsText(int from) {
        return String.join(" ", params.subList(from, params.size()));
    }

    public String getParamsText(int from, int to) {
        return String.join(" ", params.subList(from, to));
    }

    @Override
    public String toString() {
        return "UserInputCommand{" +
                "command='" + command + '\'' +
                ", action='" + action + '\'' +
                ", params=" + params +
                '}';
    }
}
