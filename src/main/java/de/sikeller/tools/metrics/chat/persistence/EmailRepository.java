package de.sikeller.tools.metrics.chat.persistence;

import com.arangodb.springframework.repository.ArangoRepository;
import de.sikeller.tools.metrics.chat.persistence.dao.mail.EmailDao;

public interface EmailRepository extends ArangoRepository<EmailDao, String> {
}
