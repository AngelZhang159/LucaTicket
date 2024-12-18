package com.lucaticket.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa la respuesta tras eliminar un
 * usuario.
 * Este objeto contiene la confirmación de la eliminación junto con algunos
 * datos básicos del usuario eliminado.
 * 
 * @author Angel
 * @version 2.0
 * @since 11-12-2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserResponse {

	/**
	 * Mensaje que confirma que el usuario ha sido eliminado correctamente.
	 * Este campo es constante y no se puede modificar.
	 */
	private final String mensaje = "El usuario ha sido borrado correctamente";

	/**
	 * Nombre completo del usuario eliminado.
	 */
	private String fullName;

	/**
	 * Dirección de correo electrónico del usuario eliminado.
	 */
	private String mail;

}
