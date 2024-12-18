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

/**
 * Implementación del servicio {@link UserService}.
 * Proporciona la lógica de negocio para la gestión de usuarios.
 * 
 * @author Angel
 * @version 2.0
 * @since 11-12-2024
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	/**
	 * Guarda un nuevo usuario en la base de datos.
	 * 
	 * @author Angel
	 * @param userRequest Datos del usuario a registrar.
	 * @return {@code UserResponse} con los datos del usuario creado.
	 * @throws UserAlreadyExistsException si el email ya está registrado.
	 * @since 18-12-2024
	 */
	@Override
	public ResponseEntity<UserResponse> saveUser(UserRequest userRequest) {
		if (userRepository.findById(userRequest.getMail()).isPresent()) {
			throw new UserAlreadyExistsException("El email ya está registrado: " + userRequest.getMail());
		}

		return new ResponseEntity<>(userRepository.save(userRequest.toEntity()).toDto(), HttpStatus.CREATED);
	}

	/**
	 * Obtiene los datos de un usuario por su email.
	 * 
	 * @author Raul
	 * @param email Email del usuario a buscar.
	 * @return {@code UserResponse} con los datos del usuario.
	 * @throws UserNotFoundException si no se encuentra el usuario.
	 * @since 18-12-2024
	 */
	@Override
	public ResponseEntity<UserResponse> getUser(String email) {
		return new ResponseEntity<>(userRepository.findById(email)
				.orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el email: " + email))
				.toDto(), HttpStatus.ACCEPTED);
	}

	/**
	 * Actualiza los datos de un usuario.
	 * 
	 * @author Angel
	 * @param email             Email del usuario a actualizar.
	 * @param updateUserRequest Datos nuevos para el usuario.
	 * @return {@code UpdateUserResponse} con los datos actualizados.
	 * @throws InvalidUserDataException si la contraseña actual es incorrecta.
	 * @since 18-12-2024
	 */
	@Override
	public ResponseEntity<UpdateUserResponse> update(String email, UpdateUserRequest updateUserRequest) {
		comprobarContrasenia(email, updateUserRequest);

		User userOld = userRepository.findById(email).orElseThrow(
				() -> new UserNotFoundException("No se ha encontrado el usuario con el email: " + email));

		if (updateUserRequest.getName() != null && !updateUserRequest.getName().isBlank()
				&& !updateUserRequest.getName().equals(userOld.getName())
				&& updateUserRequest.getName().length() <= 10) {
			userOld.setName(updateUserRequest.getName());
		}

		if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isBlank()
				&& !updateUserRequest.getLastName().equals(userOld.getLastName())
				&& updateUserRequest.getLastName().length() <= 10) {
			userOld.setLastName(updateUserRequest.getLastName());
		}

		if (updateUserRequest.getNewPassword() != null && !updateUserRequest.getNewPassword().isBlank()
				&& !updateUserRequest.getNewPassword().equals(userOld.getPassword())) {
			userOld.setPassword(updateUserRequest.getNewPassword());
		}

		return new ResponseEntity<>(userRepository.save(userOld).toUpdateDto(), HttpStatus.ACCEPTED);
	}

	/**
	 * Verifica que la contraseña actual sea válida.
	 * 
	 * @author Raul
	 * @param email             Email del usuario.
	 * @param updateUserRequest Solicitud de actualización de datos.
	 * @throws InvalidUserDataException si la contraseña actual es incorrecta.
	 * @since 18-12-2024
	 */
	private void comprobarContrasenia(String email, UpdateUserRequest updateUserRequest) {
		String actualPassword = userRepository.findById(email)
				.orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el email: " + email))
				.getPassword();

		if (!updateUserRequest.getPassword().equals(actualPassword)) {
			throw new InvalidUserDataException("La contraseña es incorrecta.");
		}
	}

	/**
	 * Elimina un usuario de la base de datos.
	 * 
	 * @author Raul
	 * @param email Email del usuario a eliminar.
	 * @return {@code DeleteUserResponse} con los datos del usuario eliminado.
	 * @throws UserNotFoundException si no se encuentra el usuario.
	 * @since 18-12-2024
	 */
	@Override
	public ResponseEntity<DeleteUserResponse> delete(String email) {
		DeleteUserResponse respuesta = userRepository.findById(email)
				.orElseThrow(() -> new UserNotFoundException(
						"El email proporcionado no coincide con ningún usuario en la base de datos."))
				.toDeleteDto();

		userRepository.deleteById(email);

		return new ResponseEntity<>(respuesta, HttpStatus.ACCEPTED);
	}
}
