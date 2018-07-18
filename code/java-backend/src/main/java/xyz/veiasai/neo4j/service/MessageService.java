package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Message;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.repositories.MessageRepository;
import java.util.Date;
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
    public Message addMessage(Message message){
        message.setId(null);
        message.setState(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setDate(df.format(new Date()));

        message=messageRepository.save(message);
        return message;
    }

    @Transactional(readOnly = true)
    public void deleteMessage(String authorId,String messageId){
        messageRepository.deleteMessage(authorId, messageId);
    }




}
