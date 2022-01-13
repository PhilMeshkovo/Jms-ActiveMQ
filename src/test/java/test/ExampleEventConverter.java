package test;

import com.phil.queue.Converter;
import com.phil.queue.ExampleEvent;

import javax.jms.JMSException;
import javax.jms.Message;

public class ExampleEventConverter implements Converter<ExampleEvent> {

    @Override
    public ExampleEvent convert(Message message) throws JMSException {
        ExampleEvent event = new ExampleEvent();
        event.setEventId(message.getStringProperty("eventId"));
        event.setDocumentId(message.getStringProperty("documentId"));
        event.setEventText(message.getStringProperty("eventText"));
        return event;
    }

    @Override
    public Message convert(ExampleEvent message, Message toMessage) throws JMSException {
        toMessage.setStringProperty("eventId", message.getEventId());
        toMessage.setStringProperty("documentId", message.getDocumentId());
        toMessage.setStringProperty("eventText", message.getEventText());
        return toMessage;
    }
}
