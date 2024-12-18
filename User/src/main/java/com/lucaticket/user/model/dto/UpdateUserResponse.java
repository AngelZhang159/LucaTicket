package com.lucaticket.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa la respuesta después de actualizar
 * los datos de un usuario.
 * Este objeto se utiliza para confirmar que la actualización se realizó
 * correctamente y muestra los nuevos datos actualizados.
 * 
 * @author Raul
 * @version 2.0
 * @since 17-12-2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

	/**
	 * Mensaje predeterminado que confirma la actualización del usuario.
	 * Incluye una advertencia de que se muestra la nueva contraseña para
	 * validación.
	 */
	private final String mensaje = "El usuario ha sido actualizado correctamente *** MOSTRAMOS LA CONTRASEÑA PARA QUE SE VEA QUE HA CAMBIADO ***";

	/**
	 * Dirección de correo electrónico del usuario actualizado.
	 */
	private String email;

	/**
	 * Nombre completo del usuario actualizado, que combina el nombre y el apellido.
	 */
	private String fullName;

	/**
	 * Nueva contraseña del usuario actualizado.
	 * Se muestra únicamente para confirmar el cambio realizado.
	 */
	private String password;

}
