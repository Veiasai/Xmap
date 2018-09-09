package xyz.xmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xmap.domain.Message;
import xyz.xmap.repositories.AuthorRepository;
import xyz.xmap.repositories.BuildingRepository;
import xyz.xmap.repositories.MessageRepository;

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

    @Transactional()
    public Message addMessage(Message message) {
        message.setId(null);
        message.setState(1);    //状态默认设为1即有效
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));     //设置信息日期
        message = messageRepository.save(message);
        return message;
    }

    @Transactional()
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
        return messageRepository.findMessageByBuildingAndAuthor(buildingId, authorId,title, skip, limit);
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
        return messageRepository.countAuthorAndMessage(authorId, messageId) != 0;
    }
}
