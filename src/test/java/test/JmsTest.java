package test;

import com.phil.queue.ExampleEvent;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;
import javax.jms.Session;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JmsTest {

    private static final String QUEUE_URL = "tcp://localhost:61616";

    private static final String QUEUE_NAME = "test-queue-333";

    private static final String QUEUE_USER = "admin";

    private static final String QUEUE_PASS = "admin";

    @AfterEach
    @BeforeEach
    public void wipeTestQueue() throws JMSException {

        var factory = new ActiveMQConnectionFactory(QUEUE_USER, QUEUE_PASS, QUEUE_URL);
        try (var connection = factory.createConnection();
             var session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE)) {
            connection.start();

            ActiveMQDestination queue = (ActiveMQDestination) session.createQueue(QUEUE_NAME);
            ((ActiveMQConnection) connection).destroyDestination(queue);

            session.commit();
        }
    }

    @Test
    public void send_receive_message_raw() throws JMSException {
        var factory = new ActiveMQConnectionFactory(QUEUE_USER, QUEUE_PASS, QUEUE_URL);
        try (var connection = factory.createConnection();
             var session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE)) {

            connection.start();

            var queue = session.createQueue(QUEUE_NAME);
            var producer = session.createProducer(queue);

            UUID messageId = UUID.randomUUID();

            var messageOut = new ActiveMQMessage();
            messageOut.setStringProperty("eventId", "55777");
            messageOut.setStringProperty("documentId", "new-356");
            messageOut.setStringProperty("eventText", "hello");

            producer.send(messageOut);
            session.commit();

            var consumer = session.createConsumer(queue);
            var messageIn = consumer.receive(1000L);

            assertTrue(messageIn instanceof ActiveMQMessage);
            messageIn.acknowledge();
            session.commit();

            ExampleEventConverter converter = new ExampleEventConverter();
            ExampleEvent event = converter.convert(messageIn);
            assertEquals("55777", event.getEventId());
            assertEquals("new-356", event.getDocumentId());
            assertEquals("hello", event.getEventText());
        }
    }
}
