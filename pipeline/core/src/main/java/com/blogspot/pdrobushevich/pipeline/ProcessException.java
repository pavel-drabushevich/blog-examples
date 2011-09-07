package com.blogspot.pdrobushevich.pipeline;

public class ProcessException extends RuntimeException {

    public ProcessException(final String message) {
        super(message);
    }

    public ProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProcessException(final Throwable cause) {
        super(cause);
    }
}