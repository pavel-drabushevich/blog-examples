package com.blogspot.pdrobushevich.pipeline;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AbstractFlowBuilderTest {

    @Test(expected = InitException.class)
    public void testNullReader() {
        AbstractFlowBuilder builder = builder();
        builder.reader(null);
    }

    @Test(expected = InitException.class)
    public void testNullTransform() {
        AbstractFlowBuilder builder = builder();
        builder.transform(null);
    }

    @Test(expected = InitException.class)
    public void testNullWriter() {
        AbstractFlowBuilder builder = builder();
        builder.writer(null);
    }


    @Test(expected = InitException.class)
    public void testMissReader() {
        AbstractFlowBuilder builder = builder();
        builder.writer(writer());
        builder.build();
    }

    @Test(expected = InitException.class)
    public void testMissWriter() {
        AbstractFlowBuilder builder = builder();
        builder.reader(reader());
        builder.build();
    }

    @Test
    public void testWithoutTransform() {
        testFlow(reader(), writer());
    }

    @Test
    public void testFullFlow() {
        testFlow(reader(), writer(), transform(), transform());
    }

    private void testFlow(final Reader reader, final Writer writer, final Transform... transforms) {
        AbstractFlowBuilder builder = builder();
        builder.reader(reader);
        for (final Transform transform : transforms)
            builder.transform(transform);
        builder.writer(writer);
        com.blogspot.pdrobushevich.pipeline.Flow flow = builder.build();
        assertNotNull(flow);
        verify(builder).flow(reader, transforms, writer);
    }

    private AbstractFlowBuilder builder() {
        return spy(new MockFlowBuilder(mock(Flow.class)));
    }

    private Reader reader() {
        return mock(Reader.class);
    }

    private Writer writer() {
        return mock(Writer.class);
    }

    private Transform transform() {
        return mock(Transform.class);
    }

    protected static class MockFlowBuilder extends AbstractFlowBuilder {

        private final Flow mockFlow;

        public MockFlowBuilder(final Flow mockFlow) {
            this.mockFlow = mockFlow;
        }

        @Override
        protected Flow flow(final Reader reader, final Transform[] transforms, final Writer writer) {
            return mockFlow;
        }
    }

}
