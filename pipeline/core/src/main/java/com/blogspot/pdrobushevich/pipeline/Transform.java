package com.blogspot.pdrobushevich.pipeline;

public interface Transform {

    Document apply(final Document document) throws ProcessException;

}
