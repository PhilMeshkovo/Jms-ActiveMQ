package com.phil.queue;

public interface JmsConsumer<Type> {
    Type read(int timeout);
}
