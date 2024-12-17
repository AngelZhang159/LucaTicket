package com.lucaticket.user.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucaticket.user.error.InvalidUserDataException;
import com.lucaticket.user.error.UserAlreadyExistsException;
import com.lucaticket.user.error.UserNotFoundException;
import com.lucaticket.user.model.User;
import com.lucaticket.user.model.dto.DeleteUserResponse;
import com.lucaticket.user.model.dto.UpdateUserRequest;
import com.lucaticket.user.model.dto.UpdateUserResponse;
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
		return new ResponseEntity<>(userRepository.findById(email)
				.orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el email: " + email))
				.toDto(), HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<UpdateUserResponse> update(String email, UpdateUserRequest updateUserRequest) {
		comprobadorContrasenia(email, updateUserRequest);
		User userOld = userRepository.findById(email).get();

		if (updateUserRequest.getName() != null && !(updateUserRequest.getName().equals(userOld.getName()))
				&& !updateUserRequest.getName().isBlank() && !(updateUserRequest.getName().length() > 10)) {
			userOld.setName(updateUserRequest.getName());
		}
		if (updateUserRequest.getLastName() != null && !(updateUserRequest.getLastName().equals(userOld.getLastName()))
				&& !updateUserRequest.getLastName().isBlank() && !(updateUserRequest.getLastName().length() > 10)) {
			userOld.setLastName(updateUserRequest.getLastName());
		}
		if (updateUserRequest.getNewPassword() != null
				&& !(updateUserRequest.getNewPassword().equals(userOld.getPassword()))
				&& !updateUserRequest.getNewPassword().isBlank()) {
			userOld.setPassword(updateUserRequest.getNewPassword());
		}

		return new ResponseEntity<>(userRepository.save(userOld).toUpdateDto(), HttpStatus.ACCEPTED);
	}

	private void comprobadorContrasenia(String email, UpdateUserRequest updateUserRequest) {
		if (updateUserRequest.getPassword()
				.equals(userRepository.findById(email).orElseThrow(
						() -> new UserNotFoundException("No se ha encontrado el usuario con el email: " + email))
						.getPassword())) {
		} else {
			throw new InvalidUserDataException("La contraseña es incorrecta");
		}
	}

	@Override
	public ResponseEntity<DeleteUserResponse> delete(String email) {
		DeleteUserResponse respuesta = userRepository.findById(email)
				.orElseThrow(() -> new UserNotFoundException("El email proporcionado no coincide con ningun usuario en la base de datos"))
				.toDeleteDto();
		userRepository.deleteById(email);
		return new ResponseEntity<>(respuesta, HttpStatus.ACCEPTED);
	}

}