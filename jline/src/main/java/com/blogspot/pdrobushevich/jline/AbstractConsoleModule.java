package com.blogspot.pdrobushevich.jline;

import com.blogspot.pdrobushevich.jline.command.Command;
import com.blogspot.pdrobushevich.jline.command.CommandsCompletor;
import com.blogspot.pdrobushevich.jline.command.Executor;
import com.blogspot.pdrobushevich.jline.command.ExitCommand;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import jline.Completor;

public abstract class AbstractConsoleModule extends AbstractModule {

    @Override
    protected final void configure() {
        MapBinder<String, Command> commandsBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);

        commandsBinder.addBinding("exit").toInstance(new ExitCommand());

        for (Command command : getCommands()) {
            commandsBinder.addBinding(command.getName()).toInstance(command);
        }

        bind(Completor.class).to(CommandsCompletor.class);
        bind(Executor.class).to(Executor.class);
        bind(Console.class).to(Console.class);
    }

    protected abstract Command[] getCommands();

}
