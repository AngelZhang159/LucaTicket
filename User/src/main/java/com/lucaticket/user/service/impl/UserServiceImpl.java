package com.lucaticket.user.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.user.error.UserAlreadyExistsException;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.repository.UserRepository;
import com.lucaticket.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	/**
	 * Guarda un nuevo usuario
	 * 
	 * @author Angel
	 * @param UserRequest
	 * @return UserResponse
	 */
	@Override
	public ResponseEntity<UserResponse> saveUser(UserRequest userRequest) {
		// @Olivord
		if (userRepository.findById(userRequest.getMail()).isPresent()) {
			throw new UserAlreadyExistsException("El email ya está registrado " + userRequest.getMail());
		}

		return new ResponseEntity<>(userRepository.save(userRequest.toEntity()).toDto(), HttpStatus.CREATED);
	}

	/**
	 * @author Raul
	 * @param email del usuario que se quiere buscar
	 * @return un usuario con ese email en caso de que exista
	 */
	@Override
	public ResponseEntity<UserResponse> getUser(String email) {
		return new ResponseEntity<>(userRepository.findById(email).orElseThrow().toDto(), HttpStatus.ACCEPTED);
	}

}