package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Transform;
import org.junit.Test;

import static com.blogspot.pdrobushevich.pipeline.FlowUtils.transform;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransformActorTest implements EvaluateListener {

    @Test
    public synchronized void testApply() throws Exception {
        final Document doc = new Document();
        final Transform transform = transform(doc);
        final Evaluator next = mock(Evaluator.class);
        Evaluator transformActor = TypedActor.newInstance(Evaluator.class,
                TransformActor.factory(transform, next, this));
        transformActor.apply(doc);
        transformActor.complete();
        wait();
        verify(next).apply(doc);
        verify(next, times(0)).apply(null);
        verify(next).complete();
    }

    @Override
    public synchronized void onComplete() {
        notify();
    }
}
