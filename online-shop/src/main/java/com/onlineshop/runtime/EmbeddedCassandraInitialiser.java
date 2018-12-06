package com.onlineshop.runtime;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

public final class EmbeddedCassandraInitialiser {

    private EmbeddedCassandraInitialiser() {
    }

    public static void initialiseEmbeddedCassandra() {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        } catch (Exception e) {
            throw new EmbeddedCassandraInitiatisationException(
                    "There was an error when initialising embedded Cassandra.", e);
        }
    }

}
