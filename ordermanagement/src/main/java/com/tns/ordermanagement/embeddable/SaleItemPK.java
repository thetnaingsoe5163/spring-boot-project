package com.tns.ordermanagement.embeddable;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class SaleItemPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID saleId;
	private int itemId;
}
