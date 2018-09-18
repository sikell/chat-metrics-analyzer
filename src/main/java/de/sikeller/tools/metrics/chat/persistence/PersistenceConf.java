package de.sikeller.tools.metrics.chat.persistence;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"de.sikeller.tools.metrics.chat.persistence"})
public class PersistenceConf extends AbstractArangoConfiguration {
    private final PersistenceProperties properties;

    @Autowired
    public PersistenceConf(PersistenceProperties properties) {
        this.properties = properties;
    }

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder()
                .host(properties.getHost(), properties.getPort())
                .user(properties.getUser())
                .password(properties.getPassword());
    }

    @Override
    public String database() {
        return properties.getDatabase();
    }
}
