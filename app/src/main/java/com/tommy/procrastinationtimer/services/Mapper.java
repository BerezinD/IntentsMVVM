package com.tommy.procrastinationtimer.services;

import java.util.List;

public interface Mapper<F, T> {
    public T map(F from);

    public List<T> map(List<F> from);

    public T[] map(F... from);
}
