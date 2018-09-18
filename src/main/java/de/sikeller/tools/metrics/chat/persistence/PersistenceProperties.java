package de.sikeller.tools.metrics.chat.persistence;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("metrics.chat.persistence")
public class PersistenceProperties {
    private String database;
    private String user;
    private String password;
    private String host;
    private Integer port;
}
