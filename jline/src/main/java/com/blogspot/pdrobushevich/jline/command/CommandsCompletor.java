package com.blogspot.pdrobushevich.jline.command;

import com.google.inject.Inject;
import jline.ArgumentCompletor;
import jline.Completor;
import jline.NullCompletor;
import jline.SimpleCompletor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.arraycopy;

/**
 * At first complete command name.<br/>
 * Then choose for command argument completor<br/>
 * Each command may has own list of completors which are aggregated to argument completor
 */
public class CommandsCompletor implements Completor {

    private final ArgumentCompletor.ArgumentDelimiter delim;

    private final Completor commandCompletor;
    private final Map<String, Completor> argumentsCompletors;

    @Inject
    public CommandsCompletor(final Map<String, Command> commands) {
        delim = new ArgumentCompletor.WhitespaceArgumentDelimiter();

        String[] commandNames = new String[commands.size()];
        commandNames = commands.keySet().toArray(commandNames);
        commandCompletor = new SimpleCompletor(commandNames);
        argumentsCompletors = new HashMap<String, Completor>();
        for (Map.Entry<String, Command> commandEntry : commands.entrySet()) {
            String commandName = commandEntry.getKey();
            Command command = commandEntry.getValue();
            Completor[] baseArgumentsCompletors = command.getArgCompletors();
            int baseLength = baseArgumentsCompletors == null ? 0 : baseArgumentsCompletors.length;
            Completor[] commandArgumentCompletors = new Completor[baseLength + 2];

            commandArgumentCompletors[0] = commandCompletor;
            if (baseLength > 0) {
                arraycopy(baseArgumentsCompletors, 0, commandArgumentCompletors, 1, baseLength);
            }
            commandArgumentCompletors[baseLength + 1] = new NullCompletor();

            Completor argumentCompletor = new ArgumentCompletor(commandArgumentCompletors);
            argumentsCompletors.put(commandName, argumentCompletor);
        }
    }

    @Override
    public int complete(final String buffer, final int cursor, final List candidates) {
        ArgumentCompletor.ArgumentList list = delim.delimit(buffer, cursor);
        int argIndex = list.getCursorArgumentIndex();

        if (argIndex < 0) {
            return -1;
        }

        if (argIndex > 0) {
            String commandName = list.getArguments()[0];
            if (!argumentsCompletors.containsKey(commandName)) {
                return -1;
            }
            return argumentsCompletors.get(commandName).complete(buffer, cursor, candidates);
        } else {
            return commandCompletor.complete(buffer, cursor, candidates);
        }
    }


}
