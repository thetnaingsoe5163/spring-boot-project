package com.tns.ordermanagement.model.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tns.ordermanagement.model.entity.Sale;
import com.tns.ordermanagement.model.entity.constant.Status;

public interface SaleRepo extends BaseRepo<Sale, UUID> {

	List<Sale> findByStatus(Status pending);

	Optional<Sale> findOneById(UUID id);

}
