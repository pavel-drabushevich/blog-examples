package com.blogspot.pdrobushevich.jline.command;

import com.blogspot.pdrobushevich.jline.Console;
import com.google.common.collect.Lists;
import org.junit.Test;

import static com.blogspot.pdrobushevich.jline.command.HelpCommand.HELP_FORMAT;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static java.util.Arrays.asList;

public class HelpCommandTest {

    @Test
    public void testNotInitialized() throws InterruptedException {
        HelpCommand command = new HelpCommand();
        assertNull(command.execute(null));
    }

    @Test
    public void testNull() throws InterruptedException {
        HelpCommand command = new HelpCommand();
        command.init(null);
        assertNull(command.execute(null));
    }

    @Test
    public void testEmpty() throws InterruptedException {
        HelpCommand command = new HelpCommand();
        command.init(Lists.<Command>newArrayList());
        assertNull(command.execute(null));
    }

    @Test
    public void testCommandWithoutArgs() throws InterruptedException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.init(asList(createMockCommand("test", "desc")));
        String expectedHelp = String.format(HELP_FORMAT, "test", "desc") + LINE_SEPARATOR;
        assertEquals(expectedHelp, helpCommand.execute(null));
    }

    @Test
    public void testCommandWithArgs() throws InterruptedException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.init(asList(createMockCommand("test", "desc", "arg1", "arg2")));
        String expectedHelp = String.format(HELP_FORMAT, "test <arg1> <arg2>", "desc") + LINE_SEPARATOR;
        assertEquals(expectedHelp, helpCommand.execute(null));
    }

    @Test
    public void testFewCommands() throws InterruptedException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.init(asList(createMockCommand("test1", "desc1", "arg1"),
                createMockCommand("test2", "desc2", "arg2")));
        String expectedHelp = String.format(HELP_FORMAT, "test1 <arg1>", "desc1") + LINE_SEPARATOR +
                String.format(HELP_FORMAT, "test2 <arg2>", "desc2") + LINE_SEPARATOR;
        assertEquals(expectedHelp, helpCommand.execute(null));
    }

    public Command createMockCommand(final String name, final String description, final String... arguments) {
        Command command = mock(Command.class);
        when(command.getName()).thenReturn(name);
        when(command.getDescription()).thenReturn(description);
        when(command.getArgs()).thenReturn(arguments);
        return command;
    }

}
