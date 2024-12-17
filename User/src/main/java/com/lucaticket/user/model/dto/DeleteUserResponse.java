package com.lucaticket.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserResponse {

	private final String mensaje = "El usuario ha sido borrado correctamente";
	private String fullName;
	private String mail;

}
