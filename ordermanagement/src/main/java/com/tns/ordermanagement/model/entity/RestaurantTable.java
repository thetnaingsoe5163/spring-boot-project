package com.tns.ordermanagement.model.entity;

import java.util.List;

import com.tns.ordermanagement.model.entity.constant.TableStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class RestaurantTable extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int tableNumber;
	
	@OneToMany(mappedBy = "restaurantTable")
	private List<OrderTransaction> transactions;
	
	@Enumerated(EnumType.STRING)
	private TableStatus status;
}
