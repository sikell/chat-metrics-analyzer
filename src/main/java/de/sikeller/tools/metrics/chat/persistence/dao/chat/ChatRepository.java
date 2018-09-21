package de.sikeller.tools.metrics.chat.persistence.dao.chat;

import com.arangodb.springframework.repository.ArangoRepository;

public interface ChatRepository extends ArangoRepository<ChatDao, String> {
}
