package com.jdc.online.balances.model.repo;

import java.util.List;
import java.util.Optional;

import com.jdc.online.balances.model.BaseRepository;
import com.jdc.online.balances.model.entity.District;
import com.jdc.online.balances.model.entity.Region;

public interface DistrictRepo extends BaseRepository<District, Integer> {

	List<District> findByRegionId(int regionId);
	
	Optional<Region> findOneRegionById(int districtId);

}
