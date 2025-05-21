package com.jdc.online.balances.controller.member.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.jdc.online.balances.model.entity.Member;
import com.jdc.online.balances.model.entity.Township;
import com.jdc.online.balances.model.entity.consts.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileEditForm {
	
	@NotBlank(message = "Please enter name.")
	private String name;
	
	@NotNull(message = "Please enter gender.")
	private Gender gender;
	
	@NotNull(message = "Please enter date of birth.")
	private LocalDate dateOfBirth;
	
	@NotBlank(message = "Please enter phone.")
	private String phone;
	
	@NotBlank(message = "Please enter email.")
	private String email;
	
	@NotNull(message = "Please enter township.")
	private Integer township;
	
	@NotBlank(message = "Please enter address.")
	private String address;
	
	private String profileImage;
	
	public static ProfileEditForm from(Member entity) {
		return new Builder()
				.name(entity.getName())
				.gender(entity.getGender())
				.dateOfBirth(entity.getDob())
				.phone(entity.getPhone())
				.email(entity.getEmail())
				.township(Optional.ofNullable(entity.getTownship()).map(Township::getId).orElse(null))
				.address(entity.getAddress())
				.profileImage(entity.getProfileImage())
				.build();
	}

	public static class Builder {

	    private String name;
	    private Gender gender;
	    private LocalDate dateOfBirth;
	    private String phone;
	    private String email;
	    private String region;
	    private String district;
	    private Integer township;
	    private String address;
	    private String profileImage;

	    public Builder name(String name) {
	        this.name = name;
	        return this;
	    }

	    public Builder gender(Gender gender) {
	        this.gender = gender;
	        return this;
	    }

	    public Builder dateOfBirth(LocalDate dateOfBirth) {
	        this.dateOfBirth = dateOfBirth;
	        return this;
	    }

	    public Builder phone(String phone) {
	        this.phone = phone;
	        return this;
	    }

	    public Builder email(String email) {
	        this.email = email;
	        return this;
	    }

	    public Builder region(String region) {
	        this.region = region;
	        return this;
	    }

	    public Builder district(String district) {
	        this.district = district;
	        return this;
	    }

	    public Builder township(Integer township) {
	        this.township = township;
	        return this;
	    }

	    public Builder address(String address) {
	        this.address = address;
	        return this;
	    }

	    public Builder profileImage(String profileImage) {
	        this.profileImage = profileImage;
	        return this;
	    }

	    public ProfileEditForm build() {
	        ProfileEditForm form = new ProfileEditForm();
	        form.setName(name);
	        form.setGender(gender);
	        form.setDateOfBirth(dateOfBirth);
	        form.setPhone(phone);
	        form.setEmail(email);
	        form.setTownship(township);
	        form.setAddress(address);
	        form.setProfileImage(profileImage);
	        return form;
	    }
	}

}
