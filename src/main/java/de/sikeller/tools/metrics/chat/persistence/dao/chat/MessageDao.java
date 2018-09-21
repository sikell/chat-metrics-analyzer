package de.sikeller.tools.metrics.chat.persistence.dao.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
class MessageDao {
    private Date timestamp;
    private PersonDao person;
    private String message;
}
