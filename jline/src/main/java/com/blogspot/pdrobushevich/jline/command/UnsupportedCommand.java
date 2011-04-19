package com.blogspot.pdrobushevich.jline.command;

public class UnsupportedCommand extends RuntimeException {

    private final String commandName;

    public UnsupportedCommand(final String commandName) {
        super("Unsupported command: " + commandName);
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
