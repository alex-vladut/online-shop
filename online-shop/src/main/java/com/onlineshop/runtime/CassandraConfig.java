package com.onlineshop.runtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.endpoint}")
    private String endpoint;
    @Value("${cassandra.port}")
    private int port;

    @Override
    protected String getKeyspaceName() {
        return "OnlineShopKeySpace";
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        EmbeddedCassandraInitialiser.initialiseEmbeddedCassandra(endpoint, port);

        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(endpoint);
        cluster.setPort(port);
        cluster.setJmxReportingEnabled(false);
        return cluster;
    }

}