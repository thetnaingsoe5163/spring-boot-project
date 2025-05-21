package com.jdc.online.balances.controller.member.dto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.jdc.online.balances.model.entity.Member;

public record MemberProfileDetails(
		long id,
		String name,
		String phone,
		String email,
		String profileImage,
		String address,
		LocalDateTime registeredAt,
		LocalDateTime lastAccessAt
		) {

	public static MemberProfileDetails from(Member member) {
		String address = "";
		
		if(StringUtils.hasLength(member.getAddress())
				|| member.getTownship() != null) {
			
			address = Stream.of(member.getAddress(), member.getTownship().getName(), member.getTownship().getDistrict().getName(), member.getTownship().getDistrict().getRegion().getName())
						.filter(StringUtils::hasLength)
						.collect(Collectors.joining(", "));
		}

		return new MemberProfileDetails(
				member.getId(),
				member.getName(),
				member.getPhone(),
				member.getEmail(),
				member.getProfileImage(),
				address,
				member.getActivity().getRegisteredAt(),
				member.getActivity().getLastAccessedAt()
				);
	}
}
