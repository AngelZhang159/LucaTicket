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

import com.lucaticket.user.model.dto.UpdateUserRequest;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

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

	/**
	 * @author Angel
	 * @param email
	 * @return Usuario response
	 */

	@GetMapping("/{email}")
	public ResponseEntity<UserResponse> getUser(
			@PathVariable @Email(message = "El email debe de tener un formato correcto") @NotBlank(message = "El email no puede estar vacio en /user/{email}") String email) {
		return userService.getUser(email);
	}

	/**
	 * @author Angel
	 * @param email
	 * @param updateUserRequest
	 * @return usuario actualizado
	 */

	@PutMapping("update/{email}")
	public ResponseEntity<UserResponse> update(@PathVariable @Email @NotBlank String email,
			@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		return userService.update(email, updateUserRequest);
	}

	/**
	 * @author Angel
	 * @param email
	 * @return usuario borrado
	 */

	@DeleteMapping("delete/{email}")
	public ResponseEntity<UserResponse> delete(@PathVariable @Email @NotBlank String email) {
		return userService.delete(email);
	}

}
