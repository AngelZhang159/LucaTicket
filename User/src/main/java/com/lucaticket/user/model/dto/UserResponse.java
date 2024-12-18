package com.lucaticket.user.model.dto;

import java.time.LocalDate;

import com.lucaticket.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa la información de respuesta de un
 * usuario.
 * Este objeto se utiliza para transferir datos desde el backend hacia el
 * cliente.
 * 
 * @author Yuji
 * @version 1.0
 * @since 18-12-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	/** Nombre completo del usuario (nombre + apellido). */
	private String fullName;

	/** Correo electrónico del usuario. */
	private String mail;

	/** Fecha de registro del usuario. */
	private LocalDate signupDate;

	/**
	 * Convierte una entidad {@code User} en una instancia de {@code UserResponse}.
	 * 
	 * @param user La entidad {@code User} a convertir.
	 * @return Una instancia de {@code UserResponse} con los datos mapeados desde la
	 *         entidad.
	 * @since 18-12-2024
	 */
	public static UserResponse toDto(User user) {
		UserResponse respuesta = new UserResponse();
		respuesta.setFullName(user.getName() + " " + user.getLastName());
		respuesta.setMail(user.getMail());
		respuesta.setSignupDate(user.getSignupDate());
		return respuesta;
	}
}
