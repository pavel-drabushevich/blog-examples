package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import akka.actor.TypedActorFactory;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Transform;

public class TransformActor extends TypedActor implements Evaluator {

    private final Transform transform;

    private final Evaluator next;
    private final EvaluateListener listener;

    private TransformActor(final Transform transform, final Evaluator next, final EvaluateListener listener) {
        this.transform = transform;
        this.next = next;
        this.listener = listener;
    }

    @Override
    public void apply(final Document document) {
        next.apply(transform.apply(document));
    }

    @Override
    public void complete() {
        next.complete();
        listener.onComplete();
    }

    public static TypedActorFactory factory(final Transform transform, final Evaluator next, final EvaluateListener listener) {
        return new TypedActorFactory() {
            @Override
            public TypedActor create() {
                return new TransformActor(transform, next, listener);
            }
        };
    }
}
