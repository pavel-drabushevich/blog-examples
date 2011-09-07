package com.blogspot.pdrobushevich.pipeline;

import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class FlowUtils {

    private FlowUtils() {
    }

    public static Reader reader(final Document... docs) {
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

    public static Writer writer(final Document... docs) {
        final Writer writer = mock(Writer.class);
        for (final Document doc : docs) {
            doNothing().when(writer).write(doc);
        }
        return writer;
    }

    public static Transform transform(final Document... docs) {
        final Transform transform = mock(Transform.class);
        for (final Document doc : docs) {
            when(transform.apply(doc)).thenReturn(doc);
        }
        return transform;
    }

}
