package com.blogspot.pdrobushevich.pipeline;

public interface Writer {

    void write(final Document document) throws ProcessException;

}
