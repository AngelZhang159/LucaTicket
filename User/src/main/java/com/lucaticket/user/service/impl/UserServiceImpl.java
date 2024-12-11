package com.lucaticket.user.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.repository.UserRepository;
import com.lucaticket.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public ResponseEntity<UserResponse> saveUser(UserRequest userRequest) {
		return new ResponseEntity<UserResponse>(userRepository.save(userRequest.toEntity()).toDto(),HttpStatus.CREATED);
	}

}
