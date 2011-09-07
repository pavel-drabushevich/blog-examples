package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Writer;

public class WriterActor extends TypedActor implements Evaluator {

    private final Writer writer;

    private final EvaluateListener listener;

    private WriterActor(final Writer writer, final EvaluateListener listener) {
        this.writer = writer;
        this.listener = listener;
    }

    @Override
    public void apply(final Document document) {
        writer.write(document);
    }

    @Override
    public void complete() {
        listener.onComplete();
    }

    public static TypedActorFactory factory(final Writer writer, final EvaluateListener listener) {
        return new TypedActorFactory() {
            @Override
            public TypedActor create() {
                return new WriterActor(writer, listener);
            }
        };
    }
}