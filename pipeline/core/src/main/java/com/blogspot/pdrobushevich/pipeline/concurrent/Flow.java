package com.blogspot.pdrobushevich.pipeline.concurrent;

import akka.actor.TypedActor;
import com.blogspot.pdrobushevich.pipeline.AbstractFlowBuilder;
import com.blogspot.pdrobushevich.pipeline.ProcessException;
import com.blogspot.pdrobushevich.pipeline.Reader;
import com.blogspot.pdrobushevich.pipeline.Transform;
import com.blogspot.pdrobushevich.pipeline.Writer;

public class Flow implements com.blogspot.pdrobushevich.pipeline.Flow {

    private final ReaderEvaluator reader;
    private final CompleteListener completeListener;

    public Flow(final ReaderEvaluator reader, final CompleteListener completeListener) {
        this.reader = reader;
        this.completeListener = completeListener;
    }

    @Override
    public void run() throws ProcessException {
        reader.read();
        try {
            completeListener.waitToComplete();
        } catch (InterruptedException e) {
            throw new ProcessException("Flow probably not competed.", e);
        }
    }

    public static AbstractFlowBuilder builder() {
        return new Builder();
    }

    private static class Builder extends AbstractFlowBuilder {

        @Override
        protected Flow flow(final Reader reader, final Transform[] transforms, final Writer writer) {
            final CompleteListener completeListener = new CompleteListener();
            final Evaluator writerEvaluator = TypedActor.newInstance(Evaluator.class,
                    WriterActor.factory(writer, completeListener));
            Evaluator previous = writerEvaluator;
            for (int i = transforms.length - 1; i > 0; i--) {
                final Evaluator transformEvaluator = TypedActor.newInstance(Evaluator.class,
                        TransformActor.factory(transforms[i], previous));
                previous = transformEvaluator;
            }
            final ReaderEvaluator readerEvaluator = TypedActor.newInstance(ReaderEvaluator.class,
                    ReaderActor.factory(reader, previous));
            return new Flow(readerEvaluator, completeListener);
        }
    }

    private static class CompleteListener implements EvaluateListener {

        public synchronized void waitToComplete() throws InterruptedException {
            wait();
        }

        @Override
        public synchronized void onComplete() {
            notify();
        }
    }

}
