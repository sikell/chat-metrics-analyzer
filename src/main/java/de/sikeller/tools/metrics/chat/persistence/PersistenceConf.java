package de.sikeller.tools.metrics.chat.persistence;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"de.sikeller.tools.metrics.chat.persistence"})
public class PersistenceConf extends AbstractArangoConfiguration {
    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().host("localhost", 8529).user("root").password("test");
    }

    @Override
    public String database() {
        return "chat-metrics";
    }
}
