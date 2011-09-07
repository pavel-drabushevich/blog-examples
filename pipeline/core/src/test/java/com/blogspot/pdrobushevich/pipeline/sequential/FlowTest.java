package com.blogspot.pdrobushevich.pipeline.sequential;

import com.blogspot.pdrobushevich.pipeline.AbstractFlowBuilder;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.Reader;
import com.blogspot.pdrobushevich.pipeline.Transform;
import com.blogspot.pdrobushevich.pipeline.Writer;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlowTest {

    @Test
    public void testNothingToRead() {
        AbstractFlowBuilder builder = Flow.builder();
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
        AbstractFlowBuilder builder = Flow.builder();
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
        AbstractFlowBuilder builder = Flow.builder();
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

    private Reader reader(final Document... docs) {
        final Reader reader = mock(Reader.class);
        OngoingStubbing<Boolean> hasNextStub = when(reader.hasNext());
        for (final Document doc : docs) {
            hasNextStub = hasNextStub.thenReturn(true);
        }
        hasNextStub.thenReturn(false);
        OngoingStubbing<Document> nextStub = when(reader.next());
        for (final Document doc : docs) {
            nextStub = nextStub.thenReturn(doc);
        }
        nextStub.thenReturn(null);
        return reader;
    }

    private Writer writer(final Document... docs) {
        final Writer writer = mock(Writer.class);
        for (final Document doc : docs) {
            doNothing().when(writer).write(doc);
        }
        return writer;
    }

    private Transform transform(final Document... docs) {
        final Transform transform = mock(Transform.class);
        for (final Document doc : docs) {
            when(transform.apply(doc)).thenReturn(doc);
        }
        return transform;
    }

}
