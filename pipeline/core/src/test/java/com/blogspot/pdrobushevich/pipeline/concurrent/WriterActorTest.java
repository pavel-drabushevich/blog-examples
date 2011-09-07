package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Writer;
import org.junit.Test;

import static com.blogspot.pdrobushevich.pipeline.FlowUtils.writer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WriterActorTest implements EvaluateListener {

    @Test(timeout = 1000)
    public synchronized void testWrite() throws Exception {
        final Document doc = new Document();
        final Writer writer = writer(doc);
        Evaluator transformActor = TypedActor.newInstance(Evaluator.class,
                WriterActor.factory(writer, this));
        transformActor.apply(doc);
        transformActor.complete();
        wait();
        verify(writer).write(doc);
        verify(writer, times(0)).write(null);
    }

    @Override
    public synchronized void onComplete() {
        notify();
    }

}