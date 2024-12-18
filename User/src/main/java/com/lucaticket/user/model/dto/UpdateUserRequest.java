package com.lucaticket.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa una solicitud para actualizar los
 * datos de un usuario.
 * Este objeto se utiliza para recibir los datos que se desean modificar en una
 * cuenta de usuario existente.
 * 
 * @author Raul, Angel
 * @version 3.0
 * @since 16-12-2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

	/**
	 * Dirección de correo electrónico del usuario.
	 * Debe tener un formato válido y no puede estar vacío.
	 */
	@Email(message = "Debe ser una dirección de correo electrónico válida.")
	@NotBlank(message = "El correo electrónico no puede estar vacío.")
	private String email;

	/**
	 * Nombre del usuario.
	 * No puede exceder los 10 caracteres.
	 */
	@Size(max = 10, message = "El nombre no puede tener más de 10 caracteres de largo.")
	private String name;

	/**
	 * Apellido del usuario.
	 * No puede exceder los 10 caracteres.
	 */
	@Size(max = 10, message = "El apellido no puede tener más de 10 caracteres de largo.")
	private String lastName;

	/**
	 * Contraseña actual del usuario.
	 * Es obligatoria para realizar cambios en los datos de la cuenta.
	 */
	@NotBlank(message = "Debes de introducir la contraseña de la cuenta para poder hacer un cambio.")
	private String password;

	/**
	 * Nueva contraseña del usuario.
	 * Debe cumplir con los requisitos de seguridad:
	 * - Al menos una letra mayúscula.
	 * - Al menos una letra minúscula.
	 * - Al menos un número.
	 * - Al menos un carácter especial (@, $, !, %, *, ?, &, _, #, ^).
	 */
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_#^])[A-Za-z\\d@$!%*?&_#^]+$", message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.")
	private String newPassword;

}
