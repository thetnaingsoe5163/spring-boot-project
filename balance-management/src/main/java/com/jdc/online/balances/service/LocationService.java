package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.online.balances.model.entity.District;
import com.jdc.online.balances.model.entity.Region;
import com.jdc.online.balances.model.entity.Township;
import com.jdc.online.balances.model.repo.DistrictRepo;
import com.jdc.online.balances.model.repo.RegionRepo;
import com.jdc.online.balances.model.repo.TownshipRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

	private final RegionRepo region;
	private final DistrictRepo district;
	private final TownshipRepo township;
	
	public List<Region> findAllRegion() {
		return region.findAll();
	}
	
	public List<Township> findTownshipByDistrict(int districtId) {
		return township.findByDistrictId(districtId);
	}

	public List<District> findDistrictByRegion(int regionId) {
		return district.findByRegionId(regionId);
	}

	public District findDistrictByTownship(Integer townshipId) {
		if(townshipId != null) {
			return safeCall(township.findById(townshipId).map(Township::getDistrict), "District", "township id", townshipId);
		}
		return null;
	}
	
	public Township findTownshipById(Integer townshipId) {
		if(townshipId != null) {
			return safeCall(township.findById(townshipId), "Township", "id", townshipId);
		}
		return null;
	}
	
	public Region findRegionByTownshipId(Integer townshipId) {
		if(townshipId != null) {
			var townshipEntity = safeCall(township.findById(townshipId), "Township", "id", townshipId);
			return townshipEntity.getDistrict().getRegion();
		}
		return null;
	}
}
