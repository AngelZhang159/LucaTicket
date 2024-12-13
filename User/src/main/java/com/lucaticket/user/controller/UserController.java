package com.lucaticket.user.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	/**
	 * @author Angel
	 * @param UserRequest
	 * @return UserResponse el usuario creado
	 */

	@PostMapping("/save")
	public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.saveUser(userRequest);
	}

}
