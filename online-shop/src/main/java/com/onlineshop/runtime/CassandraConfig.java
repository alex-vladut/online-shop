package com.onlineshop.runtime;

import java.io.IOException;

import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS OnlineShopKeySpace "
            + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
    public static final String KEYSPACE_ACTIVATE_QUERY = "USE OnlineShopKeySpace;";

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
        try {
            initEmbeddedCassandraCluster();
        } catch (Exception e) {
            throw new EmbeddedCassandraInitiatisationException("There was an error when starting embedded Cassandra.",
                    e);
        }

        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(endpoint);
        cluster.setPort(port);
        cluster.setJmxReportingEnabled(false);
        return cluster;
    }

    private void initEmbeddedCassandraCluster() throws TTransportException, IOException, InterruptedException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints(endpoint).withPort(port).withoutJMXReporting()
                .build();
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        Thread.sleep(5000);
    }

}