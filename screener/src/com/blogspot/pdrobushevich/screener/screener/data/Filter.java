package com.blogspot.pdrobushevich.screener.screener.data;

public interface Filter<T> {

    boolean filtredIn(final T t);

}
