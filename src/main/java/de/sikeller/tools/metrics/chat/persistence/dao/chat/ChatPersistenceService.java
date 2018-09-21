package de.sikeller.tools.metrics.chat.persistence.dao.chat;

import de.sikeller.tools.metrics.chat.core.model.Chat;
import de.sikeller.tools.metrics.chat.core.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ChatPersistenceService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatPersistenceService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void saveChat(Chat chat) {
        ChatDao dao = ChatDao.builder()
                .messages(chat.getMessages().stream()
                        .map(m -> MessageDao.builder()
                                .timestamp(m.getTimestamp())
                                .message(m.getMessage())
                                .person(convert(m.getSender()))
                                .build())
                        .collect(Collectors.toList()))
                .persons(chat.getPersons().stream()
                        .map(this::convert)
                        .collect(Collectors.toSet()))
                .processed(false)
                .build();
        chatRepository.save(dao);
    }

    private PersonDao convert(Person person) {
        return PersonDao.builder()
                .name(person.getName())
                .build();
    }

}
