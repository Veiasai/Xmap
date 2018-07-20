package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Message;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.repositories.MessageRepository;
import xyz.veiasai.neo4j.result.Result;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

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
    public Message addMessage(Message message){
        message.setId(null);
        message.setState(1);    //状态默认设为1即有效
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));     //设置信息日期

        message=messageRepository.save(message);
        return message;
    }

    @Transactional(readOnly = true)
    public void deleteMessage(String authorId, String messageId){
        messageRepository.deleteMessage(authorId, messageId);
    }

    @Transactional(readOnly = true)
    public Message getMessageById(String messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existMessageAndAuthor(String authorId,String messageId){
        if(messageRepository.countMessageAndAuthor(authorId, messageId)!=0){
            return true;
        }
        return false;
    }
}
