package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Message;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.repositories.MessageRepository;

import java.util.*;
import java.text.SimpleDateFormat;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    @Transactional(readOnly = true)
    public Message addMessage(Message message) {
        message.setId(null);
        message.setState(1);    //状态默认设为1即有效
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));     //设置信息日期
        message = messageRepository.save(message);
        return message;
    }

    @Transactional(readOnly = true)
    public void deleteMessage(String authorId, String messageId) {
        messageRepository.deleteMessage(authorId, messageId);
    }

    @Transactional(readOnly = true)
    public Message getMessageById(String messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.orElse(null);
    }

    @Transactional(readOnly = true)
    public Collection<Message> findMessageByAuthorAndBuilding(String buildingId, String authorId, String title, Integer skip, Integer limit) {
        return messageRepository.findMessageByAuthorAndBuilding(buildingId, authorId,title, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<Message> findMessageByAuthorAndTitle(String authorId, String title, Integer skip, Integer limit) {
        return messageRepository.findMessageByAuthorAndTitle(authorId, title, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<Message> findMessageByBuildingAndTitle(String buildingId, String title, Integer skip, Integer limit) {
        return messageRepository.findMessageByBuildingAndTitle(buildingId, title, skip, limit);
    }

    @Transactional(readOnly = true)
    public boolean existMessageAndAuthor(String authorId, String messageId) {
        return messageRepository.countMessageAndAuthor(authorId, messageId) != 0;
    }
}
