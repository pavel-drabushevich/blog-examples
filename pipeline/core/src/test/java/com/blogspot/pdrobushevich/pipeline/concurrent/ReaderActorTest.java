package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Reader;
import org.junit.Test;

import static com.blogspot.pdrobushevich.pipeline.FlowUtils.reader;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReaderActorTest implements ReadListener {

    @Test
    public synchronized void testNothing() throws Exception {
        final Reader reader = reader();
        final Evaluator next = mock(Evaluator.class);
        ReaderEvaluator readerActor = TypedActor.newInstance(ReaderEvaluator.class,
                ReaderActor.factory(reader, next, this));
        readerActor.read();
        wait();
        verify(next, times(0)).apply(null);
    }

    @Test
    public synchronized void testRead() throws Exception {
        final Document doc1 = new Document();
        doc1.put("id", 1);
        final Document doc2 = new Document();
        doc2.put("id", 2);
        final Reader reader = reader(doc1, doc2);
        final Evaluator next = mock(Evaluator.class);
        ReaderEvaluator readerActor = TypedActor.newInstance(ReaderEvaluator.class,
                ReaderActor.factory(reader, next, this));
        readerActor.read();
        wait();
        verify(next).apply(doc1);
        verify(next).apply(doc2);
        verify(next, times(0)).apply(null);
    }

    @Override
    public void onStart() {
    }

    @Override
    public synchronized void onComplete() {
        notify();
    }
}
