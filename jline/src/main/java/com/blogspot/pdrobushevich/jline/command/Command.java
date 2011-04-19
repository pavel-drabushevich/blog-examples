package com.blogspot.pdrobushevich.jline.command;

import jline.Completor;

public interface Command {

    String getName();

    String getDescription();

    String execute(final String[] args);

    Completor[] getArgCompletors();

}
