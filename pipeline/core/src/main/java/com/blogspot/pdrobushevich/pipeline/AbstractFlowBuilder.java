package com.blogspot.pdrobushevich.pipeline;

import com.blogspot.pdrobushevich.pipeline.sequential.*;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractFlowBuilder {

    private Reader reader;
    private LinkedList<Transform> transforms = new LinkedList<Transform>();
    private Writer writer;

    public final AbstractFlowBuilder reader(final Reader reader) throws InitException {
        if (reader == null)
            throw new InitException("Reader shouldn't be null.");
        this.reader = reader;
        return this;
    }

    public final AbstractFlowBuilder transform(final Transform transform) throws InitException {
        if (transform == null)
            throw new InitException("Transformation shouldn't be null.");
        this.transforms.add(transform);
        return this;
    }

    public final AbstractFlowBuilder writer(final Writer writer) throws InitException {
        if (writer == null)
            throw new InitException("Writer shouldn't be null.");
        this.writer = writer;
        return this;
    }

    public final Flow build() throws InitException {
        if (reader == null)
            throw new InitException("Reader should initialize.");
        if (writer == null)
            throw new InitException("Writer should initialize.");
        Transform[] transforms = prepareTransforms();
        return flow(reader, transforms, writer);
    }

    protected abstract Flow flow(final Reader reader, final Transform[] transforms, final Writer writer);

    private Transform[] prepareTransforms() {
        final Transform[] transforms = new Transform[this.transforms.size()];
        final Iterator<Transform> it = this.transforms.iterator();
        int index = 0;
        while (it.hasNext()) {
            transforms[index] = it.next();
            index++;
        }
        return transforms;
    }

}
