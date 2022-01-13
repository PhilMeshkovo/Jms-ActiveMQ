package com.phil.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class JmsSenderImpl extends AbstractJmsWorker<ExampleEvent> implements JmsSender<ExampleEvent> {

    public JmsSenderImpl(ConnectionProperties properties, Converter<ExampleEvent> converter) {
        super(properties, converter);
    }


    @Override
    public void send(ExampleEvent message) {
        try (var connection = factory.createConnection();
             var session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE)) {
            connection.start();
            var queue = session.createQueue(properties.getQueue());
            var producer = session.createProducer(queue);
            Message converted = converter.convert(message, session.createMessage());
            producer.send(converted);
            session.commit();
        } catch (JMSException ex) {
            throw new RuntimeException(String.format(
                    "Exception on JMS interaction, broker: %s, queue: %s ",
                    properties.getUrl(), properties.getQueue()
            ), ex);
        }
    }
}
