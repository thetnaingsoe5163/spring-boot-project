package com.jdc.online.balances.controller.management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.jdc.online.balances.model.entity.Member;
import com.jdc.online.balances.model.entity.consts.Gender;

public record MemberDetails(
		long id,
		String name,
		LocalDate dateOfBirth,
		Gender gender,
		String phone,
		String email,
		String address,
		String township,
		String district,
		String region,
		boolean status,
		LocalDateTime registeredAt,
		LocalDateTime lastAccessAt,
		String profileImage
		) {
	
	public String getFullAddress() {
		if(StringUtils.hasLength(address)
				|| StringUtils.hasLength(township)
				|| StringUtils.hasLength(district)
				|| StringUtils.hasLength(region)) {
			
			return Stream.of(address, township, district, region)
					.filter(StringUtils::hasLength)
					.collect(Collectors.joining(", "));
		}
		return null;
	}
	
	public static MemberDetails from(Member member) {
		var township = member.getTownship();
		var district = township != null ? township.getDistrict() : null;
		var region = district != null ? district.getRegion(): null;
		
		return new MemberDetails(
				member.getId(),
				member.getName(),
				member.getDob(),
				member.getGender(),
				member.getPhone(),
				member.getEmail(),
				member.getAddress(),
				township != null ? township.getName() : "",
				district != null ? district.getName() : "",
				region != null ? region.getName(): "",
				member.getAccount().isActive(),
				member.getActivity().getRegisteredAt(),
				member.getActivity().getLastAccessedAt(),
				member.getProfileImage()
				);
	}

}
