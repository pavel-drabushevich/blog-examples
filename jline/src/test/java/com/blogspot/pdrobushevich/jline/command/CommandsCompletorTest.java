package com.blogspot.pdrobushevich.jline.command;

import com.google.common.collect.ImmutableMap;
import jline.ArgumentCompletor;
import jline.Completor;
import jline.SimpleCompletor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.google.common.collect.Lists.newArrayList;

public class CommandsCompletorTest {

    private Completor commandsCompletor;

    @Before
    public void before() {
        Command nullCommand = mock(Command.class);
        when(nullCommand.getArgCompletors()).thenReturn(null);

        Command emptyCommand = mock(Command.class);
        when(emptyCommand.getArgCompletors()).thenReturn(new Completor[0]);

        Command simpleCommand1 = mock(Command.class);
        Completor simpleArgumentCompletor = new SimpleCompletor(new String[]{"arg1", "arg2"});
        when(simpleCommand1.getArgCompletors()).thenReturn(new Completor[]{simpleArgumentCompletor});

        Command simpleCommand2 = mock(Command.class);
        when(simpleCommand2.getArgCompletors()).thenReturn(new Completor[0]);

        Map<String, Command> commands = new ImmutableMap.Builder().
                put("null", nullCommand).
                put("empty", emptyCommand).
                put("simple1", simpleCommand1).
                put("simple2", simpleCommand2).
                build();
        commandsCompletor = new CommandsCompletor(commands);
    }

    @Test
    public void testUnsupportedCommand() {
        assertEquals(-1, commandsCompletor.complete("u", 1, newArrayList()));
    }

    @Test
    public void testNullCommand() {
        List<String> candidates = newArrayList();
        assertEquals(0, commandsCompletor.complete("nul", 3, candidates));
        assertEquals(1, candidates.size());
        assertEquals("null ", candidates.get(0));

        assertEquals(-1, commandsCompletor.complete("null", 5, candidates));
    }

    @Test
    public void testEmptyCommand() {
        List<String> candidates = newArrayList();
        assertEquals(0, commandsCompletor.complete("empt", 4, candidates));
        assertEquals(1, candidates.size());
        assertEquals("empty ", candidates.get(0));

        assertEquals(-1, commandsCompletor.complete("empt", 6, candidates));
    }

    @Test
    public void testTwoCommands() {
        List<String> candidates = newArrayList();
        assertEquals(0, commandsCompletor.complete("simple", 6, candidates));
        assertEquals(2, candidates.size());
        assertEquals("simple1", candidates.get(0));
        assertEquals("simple2", candidates.get(1));

        assertEquals(-1, commandsCompletor.complete("simple2", 8, candidates));
    }

    @Test
    public void testCommandArgument() {
        List<String> candidates = newArrayList();
        assertEquals(8, commandsCompletor.complete("simple1 arg", 11, candidates));
        assertEquals(2, candidates.size());
        assertEquals("arg1", candidates.get(0));
        assertEquals("arg2", candidates.get(1));

        assertEquals(-1, commandsCompletor.complete("simple1 arg1", 13, candidates));
    }

}
