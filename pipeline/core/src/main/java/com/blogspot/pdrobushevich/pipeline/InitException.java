package com.blogspot.pdrobushevich.pipeline;

public class InitException extends RuntimeException {

    public InitException(final String message) {
        super(message);
    }

    public InitException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InitException(final Throwable cause) {
        super(cause);
    }
}
