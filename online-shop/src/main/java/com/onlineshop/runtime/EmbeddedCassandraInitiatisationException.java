package com.onlineshop.runtime;

/**
 * 
 * This exception is thrown if a problem was encountered while initialising
 * embedded Cassandra. There is nothing we can do about it and the application
 * cannot function properly without a database so an exception is thrown.
 *
 */
public class EmbeddedCassandraInitiatisationException extends RuntimeException {

	private static final long serialVersionUID = -1051900188680231356L;
	
	public EmbeddedCassandraInitiatisationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
