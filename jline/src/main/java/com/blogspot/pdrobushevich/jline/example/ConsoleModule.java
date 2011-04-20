package com.blogspot.pdrobushevich.jline.example;

import com.blogspot.pdrobushevich.jline.AbstractConsoleModule;
import com.blogspot.pdrobushevich.jline.command.Command;

public class ConsoleModule extends AbstractConsoleModule {

    @Override
    protected Command[] getCommands() {
        return new Command[]{new HelloCommand()};
    }

}
