package com.jdc.online.balances.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.online.balances.model.entity.District;
import com.jdc.online.balances.model.entity.Region;
import com.jdc.online.balances.model.entity.Township;
import com.jdc.online.balances.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("member/location")
@RequiredArgsConstructor
public class MemberLocationApi {
	
	private final LocationService service;
	
	@GetMapping("region")
	List<Region> findAllRegion() {
		return service.findAllRegion();
	}
	
	@GetMapping("township/{id}")
	List<Township> findTownshipByDistrict(@PathVariable(name = "id") int districtId) {
		return service.findTownshipByDistrict(districtId);
	}
	
	@GetMapping("district/{id}")
	List<District> findDistrictByRegion(@PathVariable(name = "id") int regionId) {
		return service.findDistrictByRegion(regionId);
	}
}
