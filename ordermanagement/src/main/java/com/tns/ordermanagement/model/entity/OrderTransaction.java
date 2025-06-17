package com.tns.ordermanagement.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.constant.OrderTransactionStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(indexes = {
		@Index(name = "idx_restaurant_table", columnList = "restaurant_table_id"),
		@Index(name = "idx_customer_session_id", columnList = "customer_session_id")
})
public class OrderTransaction extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private UUID customerSessionId;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private RestaurantTable restaurantTable; 
	
	@OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
	private List<SaleItem> items = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private OrderTransactionStatus status;
	
	private LocalDateTime startedAt;
	
	private LocalDateTime endedAt;
}
