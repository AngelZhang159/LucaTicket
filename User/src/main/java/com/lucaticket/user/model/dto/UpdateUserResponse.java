package com.lucaticket.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

	private final String mensaje = "El usuario ha sido actualizado correctamente *** MOSTRAMOS LA CONTRASEÑA PARA QUE SE VEA QUE HA CAMBIADO ***";
	private String email;
	private String fullName;
	private String password;

}
