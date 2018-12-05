package com.onlineshop.runtime;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public final class EmbeddedCassandraInitialiser {

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS OnlineShopKeySpace "
            + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
    public static final String KEYSPACE_ACTIVATE_QUERY = "USE OnlineShopKeySpace;";

    private EmbeddedCassandraInitialiser() {
    }

    public static void initialiseEmbeddedCassandra(String endpoint, int port) {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        } catch (Exception e) {
            throw new EmbeddedCassandraInitiatisationException(
                    "There was an error when initialising embedded Cassandra.", e);
        }

        final Cluster cluster = Cluster.builder().addContactPoints(endpoint).withPort(port).withoutJMXReporting()
                .build();
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
    }

}
