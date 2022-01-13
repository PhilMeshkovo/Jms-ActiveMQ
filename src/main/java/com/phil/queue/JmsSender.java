package com.phil.queue;

public interface JmsSender<Type> {

    void send(Type message);
}
