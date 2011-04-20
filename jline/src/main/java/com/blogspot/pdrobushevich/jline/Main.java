package com.blogspot.pdrobushevich.jline;

import com.blogspot.pdrobushevich.jline.example.ConsoleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.err;

public class Main {

    public static void main(final String[] args) {
        AbstractConsoleModule consoleModule;
        if (args == null || args.length == 0) {
            consoleModule = new ConsoleModule();
        } else {
            consoleModule = createModule(args[0]);
        }
        Injector injector = Guice.createInjector(consoleModule);
        Console console = injector.getInstance(Console.class);
        try {
            console.run();
        } catch (IOException e) {
            err.println(e.getMessage());
        }
    }

    private static AbstractConsoleModule createModule(final String moduleName) {
        try {
            Class<? extends AbstractConsoleModule> moduleClass = Class.forName(moduleName).
                    asSubclass(AbstractConsoleModule.class);
            Constructor<? extends AbstractConsoleModule> moduleConstructor = moduleClass.getConstructor();
            return moduleConstructor.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Incorrect module class name: " + moduleName);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Module class " + moduleName +
                    " doesn't implement :" + AbstractConsoleModule.class.getName());
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Module class " + moduleName +
                    " doesn't have constructor without arguments.");
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Couldn't create module class " + moduleName +
                    ", " + e.getMessage());
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Couldn't create module class " + moduleName +
                    ", " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Couldn't create module class " + moduleName +
                    ", " + e.getMessage());
        }
    }
}
