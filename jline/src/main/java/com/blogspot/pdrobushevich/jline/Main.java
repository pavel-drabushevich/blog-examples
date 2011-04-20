package com.blogspot.pdrobushevich.jline;

import com.blogspot.pdrobushevich.jline.example.ConsoleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;

import static java.lang.System.err;

public class Main {

    public static void main(final String[] args) {
        Injector injector = Guice.createInjector(new ConsoleModule());
        Console console = injector.getInstance(Console.class);
        try {
            console.run();
        } catch (IOException e) {
            err.println(e.getMessage());
        }
    }
}
