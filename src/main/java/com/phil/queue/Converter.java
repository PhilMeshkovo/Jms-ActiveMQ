package com.phil.queue;

import javax.jms.JMSException;
import javax.jms.Message;

public interface Converter<Type> {

    Type convert(Message message) throws JMSException;

    Message convert(Type message, Message toMessage) throws JMSException;
}
