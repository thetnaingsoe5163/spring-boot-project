package com.tns.ordermanagement.model.entity;

import java.util.List;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.constant.Status;

import jakarta.persistence.Column;
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
public class Sale extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@OneToMany(mappedBy = "sale")
	private List<SaleItem> saleItems;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.Pending;
}
