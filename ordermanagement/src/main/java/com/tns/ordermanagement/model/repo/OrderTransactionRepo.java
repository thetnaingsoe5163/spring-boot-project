package com.tns.ordermanagement.model.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.OrderTransaction;
import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;

public interface OrderTransactionRepo extends BaseRepo<OrderTransaction, UUID> {
	
	Optional<OrderTransaction> findOneByCustomerSessionId(UUID id);

	Optional<OrderTransaction> findByRestaurantTableIdAndCustomerSessionIdAndStatus(int tableId, UUID sessionId, OrderTransactionStatus inprogress);

	List<OrderTransaction> findByStatus(OrderTransactionStatus status);
}
