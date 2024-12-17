package com.lucaticket.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

	private String name;
	private String lastName;
	@NotBlank(message = "Debes de introducir la contraseña de la cuenta para poder hacer un cambio.")
	private String password;
	private String newPassword;

}
