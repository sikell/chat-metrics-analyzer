package de.sikeller.tools.metrics.chat.persistence.dao.chat;

import com.arangodb.springframework.annotation.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document("chat")
class ChatDao {
    @Id
    private String id;
    private List<MessageDao> messages;
    private Set<PersonDao> persons;

    private boolean processed;
}
