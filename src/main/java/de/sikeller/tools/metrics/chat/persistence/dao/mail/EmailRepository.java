package de.sikeller.tools.metrics.chat.persistence.dao.mail;

import com.arangodb.springframework.repository.ArangoRepository;

import java.util.List;

public interface EmailRepository extends ArangoRepository<EmailDao, String> {

    List<EmailDao> findAllByProcessedIsFalse();

}
