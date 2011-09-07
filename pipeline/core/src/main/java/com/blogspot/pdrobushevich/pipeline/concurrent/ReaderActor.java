package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;
import com.blogspot.pdrobushevich.pipeline.InitException;
import com.blogspot.pdrobushevich.pipeline.Reader;

public class ReaderActor extends TypedActor implements ReaderEvaluator {

    private final Reader reader;

    private final Evaluator evaluator;
    private final ReadListener listener;

    private ReaderActor(final Reader reader, final Evaluator evaluator, final ReadListener listener) {
        this.reader = reader;
        this.evaluator = evaluator;
        this.listener = listener;
    }

    @Override
    public void read() {
        listener.onStart();
        while (reader.hasNext()) {
            evaluator.apply(reader.next());
        }
        listener.onComplete();
    }

    public static TypedActorFactory factory(final Reader reader, final Evaluator evaluator, final ReadListener listener) {
        return new TypedActorFactory() {
            @Override
            public TypedActor create() {
                return new ReaderActor(reader, evaluator, listener);
            }
        };
    }

}