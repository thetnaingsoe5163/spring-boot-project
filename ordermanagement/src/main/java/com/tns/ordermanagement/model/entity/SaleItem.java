package com.tns.ordermanagement.model.entity;

import com.tns.ordermanagement.model.entity.embeddable.SaleItemPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SaleItem {

	@EmbeddedId
	private SaleItemPK pk;
	
	@MapsId("saleId")
	@ManyToOne
	private Sale sale;
	
	@MapsId("itemId")
	@ManyToOne
	private Item item;
	
	@Column(nullable = true)
	private String details;
	
	private int quantity;
	private int salePrice;
}
