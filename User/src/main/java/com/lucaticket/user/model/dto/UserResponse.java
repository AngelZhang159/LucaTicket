package com.lucaticket.user.model.dto;

import java.time.LocalDate;

import com.lucaticket.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String fullName;
	private String mail;
	private LocalDate signupDate;
	
	public UserResponse toEntity(User user) {
		UserResponse respuesta = new UserResponse();
		respuesta.setFullName(user.getName() + " " + user.getLastName());
		respuesta.setMail(user.getMail());
		respuesta.setSignupDate(user.getSignupDate());
		
		return this;
	}
}
