package com.blogspot.pdrobushevich.jline.example;

import com.blogspot.pdrobushevich.jline.command.Command;
import jline.Completor;
import jline.FileNameCompletor;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;

public class CutCommand implements Command {

    @Override
    public String getName() {
        return "cut";
    }

    @Override
    public String getDescription() {
        return "print file content";
    }

    @Override
    public String execute(final String[] args) throws InterruptedException {
        File file = new File(args[0]);
        if (!file.exists() && file.isFile()) {
            return "File '" + file.getAbsolutePath() + "' doesn't exist.";
        }
        try {
            return readFileToString(file);
        } catch (IOException e) {
            return "Couldn't read file content: " + e.getMessage();
        }
    }

    @Override
    public String[] getArgs() {
        return new String[]{"file name"};
    }

    @Override
    public Completor[] getArgCompletors() {
        return new Completor[]{new FileNameCompletor()};
    }

}
