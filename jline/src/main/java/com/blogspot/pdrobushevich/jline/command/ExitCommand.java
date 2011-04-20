package com.blogspot.pdrobushevich.jline.command;

import jline.Completor;

public class ExitCommand implements Command {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "close console";
    }

    @Override
    public String execute(String[] args) throws InterruptedException {
        throw new InterruptedException("exit");
    }

    @Override
    public String[] getArgs() {
        return null;
    }

    @Override
    public Completor[] getArgCompletors() {
        return null;
    }
}
