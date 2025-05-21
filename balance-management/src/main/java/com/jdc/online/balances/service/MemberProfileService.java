package com.jdc.online.balances.service;

import static com.jdc.online.balances.utils.EntityOperation.safeCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.online.balances.controller.member.dto.MemberProfileDetails;
import com.jdc.online.balances.controller.member.dto.ProfileEditForm;
import com.jdc.online.balances.model.repo.MemberRepo;
import com.jdc.online.balances.model.repo.TownshipRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileService {
	
	private final MemberRepo memberRepo;
	private final TownshipRepo townshipRepo;
	
	@PreAuthorize("hasAuthority('Admin') or #username eq authentication.name")
	public MemberProfileDetails loadProfile(String username) {
		return safeCall(memberRepo.findOneByAccountUserName(username)
				.map(MemberProfileDetails::from), 
				"Member", "Login Id", username);
	}

	@PreAuthorize("hasAuthority('Admin') or #username eq authentication.name")
	public ProfileEditForm getProfileEditForm(String username) {
		return safeCall(memberRepo.findOneByAccountUserName(username)
				.map(ProfileEditForm::from),
				"Member", "login id", username);
	}

	@Transactional
	@PreAuthorize("hasAuthority('Admin') or #username eq authentication.name")
	public void save(ProfileEditForm editForm, String username) {
		var member = safeCall(memberRepo.findOneByAccountUserName(username),
				"Member", "login id", username);
		
		member.setName(editForm.getName());
		member.setPhone(editForm.getPhone());
		member.setGender(editForm.getGender());
		
		townshipRepo.findById(editForm.getTownship())
			.ifPresent(member::setTownship);
		
		member.setAddress(editForm.getAddress());
		
		memberRepo.save(member);
	}
	
	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public void saveImage(String username, String imageFolder, MultipartFile file) {
		
		var profileImageName = getProfileImageName(username, file);
		var member = safeCall(
				memberRepo.findOneByAccountUserName(username), "Member", "username", username);
		
		var profileImagePath = Path.of(imageFolder, profileImageName);
		try {
			Files.copy(file.getInputStream(), profileImagePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		member.setProfileImage(profileImageName);
	}

	private String getProfileImageName(String username, MultipartFile file) {
		
		var fileName = file.getOriginalFilename();
		var arr = fileName.split("\\.");
		var extension = arr[arr.length - 1];
		
		var profileImageName = "%s.%s".formatted(username, extension); 
		return profileImageName;
	}
}
