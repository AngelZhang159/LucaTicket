package com.lucaticket.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
	
	@Email
	@NotBlank
	private String email;
	@Size(max = 10, message = "El nombre no puede tener más de 10 caracteres de largo")
	private String name;
	@Size(max = 10, message = "El apellido no puede tener más de 10 caracteres de largo")
	private String lastName;
	@NotBlank(message = "Debes de introducir la contraseña de la cuenta para poder hacer un cambio.")
	private String password;
	@Pattern(
	        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_#^])[A-Za-z\\d@$!%*?&_#^]+$",
	        message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial."
	    )
	private String newPassword;

}
