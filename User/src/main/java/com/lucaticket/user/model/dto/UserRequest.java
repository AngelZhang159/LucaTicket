package com.lucaticket.user.model.dto;

import java.time.LocalDate;

import com.lucaticket.user.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserRequest {

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres.")
    private String lastName;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe tener un formato válido.")
    private String mail;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_#^])[A-Za-z\\d@$!%*?&_#^]+$",
        message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial."
    )
    private String password;


    public User toEntity() {
        User user = new User();

        user.setName(this.name);
        user.setLastName(this.lastName);
        user.setMail(this.mail);
        user.setPassword(this.password);
        user.setSignupDate(LocalDate.now());

        return user;
	}
}
