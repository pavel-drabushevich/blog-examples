package com.blogspot.pdrobushevich.jline;

import com.blogspot.pdrobushevich.jline.command.*;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import jline.Completor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class AbstractConsoleModule extends AbstractModule {

    @Override
    protected final void configure() {
        List<Command> commands = new ArrayList<Command>();
        MapBinder<String, Command> commandsBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);

        ExitCommand exitCommand = new ExitCommand();
        commandsBinder.addBinding("exit").toInstance(exitCommand);
        HelpCommand helpCommand = new HelpCommand();
        commandsBinder.addBinding("help").toInstance(helpCommand);

        Command[] futureCommands = getCommands();
        for (Command command : futureCommands) {
            commandsBinder.addBinding(command.getName()).toInstance(command);
        }
        commands.add(exitCommand);
        commands.add(helpCommand);
        commands.addAll(asList(futureCommands));
        helpCommand.init(commands);

        bind(Completor.class).to(CommandsCompletor.class);
    }

    protected abstract Command[] getCommands();

}
