package com.blogspot.pdrobushevich.jline.command;

import jline.Completor;

import java.util.List;

import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class HelpCommand implements Command {

    public final static String HELP_FORMAT = "%1$-50s%2$-40s";

    private String help;

    public void init(final List<Command> commands) {
        if (commands == null || commands.size() == 0) {
            return;
        }
        StringBuilder helpBuilder = new StringBuilder();
        for (Command command : commands) {
            StringBuilder commandBuilder = new StringBuilder(command.getName());
            String[] args = command.getArgs();
            if (args != null && args.length > 0) {
                for (String arg : args) {
                    if (isNotBlank(arg)) {
                        commandBuilder.append(" <").append(arg).append(">");
                    }
                }
            }
            helpBuilder.append(String.format(HELP_FORMAT, commandBuilder.toString(), command.getDescription())).
                    append(LINE_SEPARATOR);
        }
        help = helpBuilder.toString();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "print help for commands";
    }

    @Override
    public String execute(String[] args) throws InterruptedException {
        return help;
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
