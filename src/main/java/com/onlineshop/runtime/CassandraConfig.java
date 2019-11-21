package com.onlineshop.runtime;

import static java.util.Collections.singletonList;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	private static final String KEYSPACE_NAME = "OnlineShopKeySpace";

	@Value("${cassandra.endpoint}")
	private String endpoint;
	@Value("${cassandra.port}")
	private int port;
	@Value("${cassandra.username}")
	private String username;
	@Value("${cassandra.password}")
	private String password;

	@Override
	protected String getKeyspaceName() {
		return KEYSPACE_NAME;
	}

	@Bean
	@Override
	public CassandraClusterFactoryBean cluster() {
		EmbeddedCassandraInitialiser.initialiseEmbeddedCassandra();

		final CassandraCqlClusterFactoryBean cluster = new CassandraCqlClusterFactoryBean();
		cluster.setContactPoints(endpoint);
		cluster.setPort(port);
		cluster.setUsername(username);
		cluster.setPassword(password);
		cluster.setKeyspaceCreations(getKeyspaceCreations());
		cluster.setJmxReportingEnabled(false);
		return cluster;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.onlineshop.domain" };
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return singletonList(CreateKeyspaceSpecification.createKeyspace(KEYSPACE_NAME).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication());
	}

}