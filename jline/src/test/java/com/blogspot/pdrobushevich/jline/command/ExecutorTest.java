package com.blogspot.pdrobushevich.jline.command;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static com.google.common.collect.Maps.newHashMap;

public class ExecutorTest {

    private final static Map<String, Command> EMPTY_COMMANDS = newHashMap();

    @Test
    public void testNullLine() throws InterruptedException {
        Executor executor = new Executor(EMPTY_COMMANDS);
        assertNull(executor.execute(null));
    }

    @Test
    public void testNullCommands() throws InterruptedException {
        Executor executor = new Executor(null);
        assertNull(executor.execute(null));
    }

    @Test
    public void testEmptyLine() throws InterruptedException {
        Executor executor = new Executor(EMPTY_COMMANDS);
        assertNull(executor.execute(EMPTY));
    }

    @Test(expected = UnsupportedCommand.class)
    public void testUnsupportedCommand() throws InterruptedException {
        Executor executor = new Executor(EMPTY_COMMANDS);
        executor.execute("test");
    }

    @Test
    public void testCommandWithoutArgs() throws InterruptedException {
        Command command = mock(Command.class);
        when(command.execute(new String[0])).thenReturn("test");
        Map<String, Command> commands = new ImmutableMap.Builder<String, Command>().put("test", command).build();
        Executor executor = new Executor(commands);
        assertEquals("test", executor.execute("test"));
        verify(command).execute(new String[0]);
    }

    @Test
    public void testCommandWithArgs() throws InterruptedException {
        Command command = mock(Command.class);
        String[] args = {"arg1", "arg2"};
        when(command.execute(args)).thenReturn("test");
        Map<String, Command> commands = new ImmutableMap.Builder<String, Command>().put("test", command).build();
        Executor executor = new Executor(commands);
        assertEquals("test", executor.execute("test arg1 arg2 "));
        verify(command).execute(args);
    }

    @Test
    public void testFewCommands() throws InterruptedException {
        Command command1 = mock(Command.class);
        when(command1.execute(new String[0])).thenReturn("test1");
        Command command2 = mock(Command.class);
        when(command2.execute(new String[0])).thenReturn("test2");
        Map<String, Command> commands = new ImmutableMap.Builder<String, Command>().
                put("command1", command1).put("command2", command2).build();
        Executor executor = new Executor(commands);
        assertEquals("test1", executor.execute("command1"));
        assertEquals("test2", executor.execute("command2"));
        verify(command1).execute(new String[0]);
        verify(command2).execute(new String[0]);
    }
}
