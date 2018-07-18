package xyz.veiasai.neo4j.result;

import xyz.veiasai.neo4j.domain.Message;

import java.util.Collection;

public class MessageResult extends Result {

    private Message singleMessage;

    private Collection<Message> Messages;

    public Message getSingleMessage() {
        return singleMessage;
    }

    public void setSingleMessage(Message singleMessage) {
        this.singleMessage = singleMessage;
    }

    public Collection<Message> getMessages() {
        return Messages;
    }

    public void setMessages(Collection<Message> messages) {
        Messages = messages;
    }
}
