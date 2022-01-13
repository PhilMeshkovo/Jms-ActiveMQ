package com.phil.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class JmsConsumerImpl extends AbstractJmsWorker<ExampleEvent> implements JmsConsumer<ExampleEvent> {

    private final ConnectionProperties properties;

    public JmsConsumerImpl(ConnectionProperties properties, Converter<ExampleEvent> converter) {
        super(properties, converter);
        this.properties = properties;
    }

    @Override
    public ExampleEvent read(int timeout) {
        try (var connection = factory.createConnection();
             var session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE)) {
            connection.start();

            var queue = session.createQueue(properties.getQueue());
            var consumer = session.createConsumer(queue);

            Message message = consumer.receive(timeout);
            if (message == null) {
                return null;
            }
            ExampleEvent converted = converter.convert(message);
            message.acknowledge();
            session.commit();
            return converted;
        } catch (JMSException ex) {
            throw new RuntimeException(String.format(
                    "Exception on JMS interaction, broker: %s, queue: %s ",
                    properties.getUrl(), properties.getQueue()
            ), ex);
        }
    }

}
