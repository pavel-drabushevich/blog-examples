package com.blogspot.pdrobushevich.jline.command;

import com.google.inject.Inject;

import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;
import static com.google.common.collect.Maps.newHashMap;

public class Executor {

    private final Map<String, Command> commands;

    @Inject
    public Executor(final Map<String, Command> commands) {
        if (commands == null) {
            this.commands = newHashMap();
        } else {
            this.commands = commands;
        }
    }

    /**
     * Parse line to command name and arguments
     *
     * @param line
     * @return command execution result
     * @throws UnsupportedCommand - if command isn't found
     */
    public String execute(final String line) throws UnsupportedCommand, InterruptedException {
        if (isBlank(line)) {
            return null;
        }
        String[] splited = line.split(" ");
        String commandName = splited[0];
        if (!commands.containsKey(commandName)) {
            throw new UnsupportedCommand(commandName);
        }
        Command command = commands.get(commandName);
        String[] args = command.getArgs();
        int expectedArgsLength = args == null ? 0 : args.length;
        if (splited.length - 1 != expectedArgsLength) {
            return "Command [" + commandName + "] must have " + expectedArgsLength + " arguments";
        }
        if (splited.length == 1) {
            return command.execute(new String[0]);
        } else {
            return command.execute(prepareArgs(splited));
        }
    }

    private String[] prepareArgs(final String[] splited) {
        String[] args = new String[splited.length - 1];
        for (int i = 0; i < splited.length - 1; i++) {
            args[i] = splited[i + 1].trim();
        }
        return args;
    }

}
