package de.sikeller.tools.metrics.chat.persistence.dao.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AttachmentDao {
    private String name;
    private String contentType;
    private String description;
    private String content;
}
