package com.phil.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

abstract class AbstractJmsWorker<Type> {

    protected final ConnectionProperties properties;

    protected final Converter<Type> converter;

    protected ConnectionFactory factory;

    public AbstractJmsWorker(
            ConnectionProperties properties,
            Converter<Type> converter
    ) {
        this.properties = properties;
        this.converter = converter;
        this.factory = new ActiveMQConnectionFactory(
                properties.getUser(),
                properties.getPass(),
                properties.getUrl());
    }
}
