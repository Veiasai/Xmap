package xyz.veiasai.neo4j.result;

import xyz.veiasai.neo4j.domain.Message;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MessageResult extends Result {

    private Message singleMessage;

    private Collection<Message> Messages;

    private Set<Map<String, Object>> AllMessages;

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

    public Set<Map<String, Object>> getAllMessages() {
        return AllMessages;
    }

    public void setAllMessages(Set<Map<String, Object>> allMessages) {
        AllMessages = allMessages;
    }
}
