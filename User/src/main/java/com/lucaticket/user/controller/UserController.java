package com.lucaticket.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.user.model.dto.DeleteUserResponse;
import com.lucaticket.user.model.dto.UpdateUserRequest;
import com.lucaticket.user.model.dto.UpdateUserResponse;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona los endpoints para operaciones CRUD sobre usuarios.
 * 
 * @author Angel
 * @version 1.0
 * @since 18-12-2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	// Inyección del servicio de usuario
	private final UserService userService;

	/**
	 * Crea un nuevo usuario en el sistema.
	 * 
	 * @author Angel
	 * @param userRequest Objeto con los datos necesarios para crear un usuario.
	 * @return {@code ResponseEntity<UserResponse>} con los datos del usuario
	 *         creado.
	 * @since 18-12-2024
	 */
	@PostMapping("/save")
	@Operation(description = "Guarda un usuario en la base de datos a partir de una peticion userRequest con todos los datos necesarios")
	public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.saveUser(userRequest);
	}

	/**
	 * Recupera los datos de un usuario basado en su email.
	 * 
	 * @author Angel
	 * @param email Dirección de correo electrónico del usuario.
	 * @return {@code ResponseEntity<UserResponse>} con los datos del usuario
	 *         encontrado.
	 * @throws com.lucaticket.user.error.UserNotFoundException si el usuario no
	 *                                                         existe.
	 * @since 18-12-2024
	 */
	@GetMapping("/{email}")
	@Operation(description = "Devuelve un usuario específico buscando por 'email'")
	public ResponseEntity<UserResponse> getUser(
			@PathVariable @Email(message = "El email debe de tener un formato correcto") @NotBlank(message = "El email no puede estar vacío en /user/{email}") String email) {
		return userService.getUser(email);
	}

	/**
	 * Actualiza los datos de un usuario existente.
	 * 
	 * @author Angel
	 * @param updateUserRequest Objeto con los datos actualizados del usuario.
	 * @return {@code ResponseEntity<UpdateUserResponse>} con los datos del usuario
	 *         actualizado.
	 * @throws com.lucaticket.user.error.InvalidUserDataException si la contraseña
	 *                                                            es incorrecta.
	 * @throws com.lucaticket.user.error.UserNotFoundException    si el usuario no
	 *                                                            existe.
	 * @since 18-12-2024
	 */
	@PutMapping("/update")
	public ResponseEntity<UpdateUserResponse> update(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		return userService.update(updateUserRequest.getEmail(), updateUserRequest);
	}

	/**
	 * Elimina un usuario de la base de datos.
	 * 
	 * @author Angel
	 * @param email Dirección de correo electrónico del usuario a eliminar.
	 * @return {@code ResponseEntity<DeleteUserResponse>} con la confirmación de
	 *         eliminación.
	 * @throws com.lucaticket.user.error.UserNotFoundException si el usuario no
	 *                                                         existe.
	 * @since 18-12-2024
	 */
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<DeleteUserResponse> delete(
			@PathVariable @Email(message = "El email debe de tener un formato correcto") @NotBlank(message = "El email no puede estar vacío en /delete/{email}") String email) {
		return userService.delete(email);
	}
}
