package de.sikeller.tools.metrics.chat.persistence.dao.mail;

import com.arangodb.springframework.annotation.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document("emails")
public class EmailDao {
    @Id
    private String id;
    private String from;
    private String to;
    private String cc;
    private String subject;
    private String sentDate;
    private String message;
    private List<AttachmentDao> attachments;
}
