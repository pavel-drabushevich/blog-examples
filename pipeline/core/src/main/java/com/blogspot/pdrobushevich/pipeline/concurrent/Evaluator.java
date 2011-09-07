package com.blogspot.pdrobushevich.pipeline.concurrent;

import com.blogspot.pdrobushevich.pipeline.Document;

public interface Evaluator {

    void apply(final Document document);

    void complete();

}
