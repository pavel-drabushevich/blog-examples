package com.blogspot.pdrobushevich.jline;

import com.blogspot.pdrobushevich.jline.command.Executor;
import com.blogspot.pdrobushevich.jline.command.UnsupportedCommand;
import com.google.inject.Inject;
import jline.Completor;
import jline.ConsoleReader;

import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.System.out;
import static java.lang.System.err;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class Console {

    private final ConsoleReader reader;

    private final Executor executor;
    private final String helpCommandName;

    // TODO: inject ConsoleReader, don't create
    @Inject
    public Console(final Executor executor, final Completor commandsCompletor) throws IllegalStateException {
        this.executor = executor;
        try {
            this.reader = new ConsoleReader();
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create console reader: " + e.getMessage(), e);
        }
        reader.setBellEnabled(false);
        reader.addCompletor(commandsCompletor);
        this.helpCommandName = "help";
    }

    public void run() throws IOException {
        String line;
        while ((line = reader.readLine(">>> ")) != null) {
            try {
                String result;
                try {
                    result = executor.execute(line);
                } catch (InterruptedException e) {
                    out.println("exit");
                    break;
                }
                if (isNotBlank(result)) {
                    out.println(result);
                }
            } catch (UnsupportedCommand e) {
                err.println("Unsupported command: " + e.getCommandName());
                try {
                    out.println(executor.execute(helpCommandName));
                } catch (InterruptedException e1) {
                    err.println("help command was interrupted");
                }
            }
        }
    }
}
