package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;
import com.blogspot.pdrobushevich.pipeline.Reader;

public class ReaderActor extends TypedActor implements ReaderEvaluator {

    private final Reader reader;

    private final Evaluator next;
    private final ReadListener listener;

    private ReaderActor(final Reader reader, final Evaluator next, final ReadListener listener) {
        this.reader = reader;
        this.next = next;
        this.listener = listener;
    }

    @Override
    public void read() {
        start();
        while (reader.hasNext()) {
            next.apply(reader.next());
        }
        complete();
    }

    private void start() {
        if (listener != null)
            listener.onStart();
    }

    private void complete() {
        next.complete();
        if (listener != null)
            listener.onComplete();
    }

    public static TypedActorFactory factory(final Reader reader, final Evaluator next) {
        return factory(reader, next, null);
    }

    public static TypedActorFactory factory(final Reader reader, final Evaluator next, final ReadListener listener) {
        return new TypedActorFactory() {
            @Override
            public TypedActor create() {
                return new ReaderActor(reader, next, listener);
            }
        };
    }

}