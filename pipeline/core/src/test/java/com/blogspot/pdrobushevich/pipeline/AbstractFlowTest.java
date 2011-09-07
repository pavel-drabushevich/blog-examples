package com.blogspot.pdrobushevich.pipeline;

import org.junit.Test;

import static com.blogspot.pdrobushevich.pipeline.FlowUtils.reader;
import static com.blogspot.pdrobushevich.pipeline.FlowUtils.transform;
import static com.blogspot.pdrobushevich.pipeline.FlowUtils.writer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractFlowTest {

    @Test
    public void testNothingToRead() {
        AbstractFlowBuilder builder = builder();
        final Reader reader = reader();
        builder.reader(reader);
        final Writer writer = writer();
        builder.writer(writer);
        com.blogspot.pdrobushevich.pipeline.Flow flow = builder.build();
        flow.run();
        verify(reader).hasNext();
        verify(reader, times(0)).next();
    }

    @Test
    public void testWithoutTransform() {
        Document doc = new Document();
        AbstractFlowBuilder builder = builder();
        final Reader reader = reader(doc);
        builder.reader(reader);
        final Writer writer = writer(doc);
        builder.writer(writer);
        com.blogspot.pdrobushevich.pipeline.Flow flow = builder.build();
        flow.run();
        verify(writer).write(doc);
        verify(writer, times(0)).write(null);
    }

    @Test
    public void testFullRun() {
        Document doc1 = new Document();
        doc1.put("id", 1);
        Document doc2 = new Document();
        doc2.put("id", 2);
        AbstractFlowBuilder builder = builder();
        final Reader reader = reader(doc1, doc2);
        builder.reader(reader);
        final Transform transform1 = transform(doc1, doc2);
        builder.transform(transform1);
        final Transform transform2 = transform(doc1, doc2);
        builder.transform(transform2);
        final Writer writer = writer(doc1, doc2);
        builder.writer(writer);
        com.blogspot.pdrobushevich.pipeline.Flow flow = builder.build();
        flow.run();
        verify(writer).write(doc1);
        verify(writer).write(doc2);
        verify(writer, times(0)).write(null);
    }

    protected abstract AbstractFlowBuilder builder();
}
