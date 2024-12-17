package com.lucaticket.user.service;

import org.springframework.http.ResponseEntity;

import com.lucaticket.user.model.dto.UpdateUserResponse;
import com.lucaticket.user.model.dto.UpdateUserRequest;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;

public interface UserService {

	/**
	 * @author Angel
	 * @param UserRequest
	 * @return El usuario creado
	 */
	public ResponseEntity<UserResponse> saveUser(UserRequest userRequest);
	
	/**
	 * @author Raul
	 * @param email del usuario que se quiere buscar
	 * @return un usuario con ese email en caso de que exista
	 */
	public ResponseEntity<UserResponse> getUser(String email);
	
	/**
	 * @author Raul
	 * @param email
	 * @param updateUserRequest
	 * @return el usuario actualizado en caso de que exista
	 */
	public ResponseEntity<UpdateUserResponse> update(String email, UpdateUserRequest updateUserRequest);
}
