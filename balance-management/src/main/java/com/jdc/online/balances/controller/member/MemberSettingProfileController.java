package com.jdc.online.balances.controller.member;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.online.balances.controller.member.dto.ProfileEditForm;
import com.jdc.online.balances.model.entity.District;
import com.jdc.online.balances.model.entity.Region;
import com.jdc.online.balances.model.entity.Township;
import com.jdc.online.balances.service.LocationService;
import com.jdc.online.balances.service.MemberProfileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member/profile")
@RequiredArgsConstructor
public class MemberSettingProfileController {
	
	private final LocationService location;
	private final MemberProfileService profile;

	@GetMapping
	String index(@ModelAttribute("townshipById") Township township) {
		System.out.println(township);
		return "member/profile/edit";
	}
	
	@PostMapping
	String updateProfile(
			@Validated @ModelAttribute(name = "profileEditForm") ProfileEditForm editForm,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/profile/edit";
		}
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		profile.save(editForm, username);
		
		return "redirect:/member/home";
	}
	
	@PostMapping("photo")
	String uploadPhoto(
			@RequestParam(name = "profileImage") MultipartFile file,
			HttpServletRequest request) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var imageFolder = request.getServletContext().getRealPath("/resources/photos");
		
		profile.saveImage(username, imageFolder, file);
		return "redirect:/member/profile";
	}
	
	@ModelAttribute("regions")
	List<Region> getAllRegion() {
		return location.findAllRegion();
	}
	
	@ModelAttribute("townshipById")
	Township getTownshipById(@ModelAttribute("profileEditForm") ProfileEditForm form) {
		return location.findTownshipById(form.getTownship());
	}
	
	@ModelAttribute("districtByTownship")
	District getDistrict(@ModelAttribute("profileEditForm") ProfileEditForm form) {
		return location.findDistrictByTownship(form.getTownship());
	}
	
	@ModelAttribute("regionByTownship")
	Region getRegion(@ModelAttribute("profileEditForm") ProfileEditForm form) {
		return location.findRegionByTownshipId(form.getTownship());
	}
	
	@ModelAttribute("profileEditForm")
	ProfileEditForm profileEditForm() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return profile.getProfileEditForm(username);
	}
}
