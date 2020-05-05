package com.onlineshop.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineshop.orders.domain.Order;

@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {

	// That query is not very efficient for large amounts of data.
	@Query("SELECT * FROM orders WHERE creationdatetime >= :startDateTime AND creationdatetime <= :endDateTime ALLOW FILTERING")
	List<Order> findAllOrdersBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	// Same query as above, only defined in a different manner
	@Query(allowFiltering = true)
	List<Order> findAllByCreationDateTimeGreaterThanEqualAndCreationDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
