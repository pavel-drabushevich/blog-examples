package com.blogspot.pdrobushevich.pipeline.sequential;

import com.blogspot.pdrobushevich.pipeline.AbstractFlowBuilder;
import com.blogspot.pdrobushevich.pipeline.Document;
import com.blogspot.pdrobushevich.pipeline.ProcessException;
import com.blogspot.pdrobushevich.pipeline.Reader;
import com.blogspot.pdrobushevich.pipeline.Transform;
import com.blogspot.pdrobushevich.pipeline.Writer;

public class Flow implements com.blogspot.pdrobushevich.pipeline.Flow {

    private final Reader reader;
    private final Transform[] transforms;
    private final Writer writer;

    private Flow(final Reader reader, final Transform[] transforms, final Writer writer) {
        this.reader = reader;
        this.transforms = transforms;
        this.writer = writer;
    }

    @Override
    public void run() throws ProcessException {
        while (reader.hasNext()) {
            Document doc = reader.next();
            for (Transform transform : transforms) {
                doc = transform.apply(doc);
            }
            writer.write(doc);
        }
    }

    public static AbstractFlowBuilder builder() {
        return new Builder();
    }

    private static class Builder extends AbstractFlowBuilder {

        @Override
        protected Flow flow(final Reader reader, final Transform[] transforms, final Writer writer) {
            return new Flow(reader, transforms, writer);
        }
    }

}
