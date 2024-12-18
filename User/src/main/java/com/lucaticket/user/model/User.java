package com.lucaticket.user.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.lucaticket.user.model.dto.DeleteUserResponse;
import com.lucaticket.user.model.dto.UpdateUserResponse;
import com.lucaticket.user.model.dto.UserResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un usuario en el sistema.
 * Esta clase utiliza JPA para mapear los datos a la base de datos.
 * 
 * @author Yuji
 * @version 2.0
 * @since 11-12-2024
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    /** 
     * Dirección de correo electrónico del usuario
     * Actúa como identificador único en la base de datos
     */
    @Id
    @Column(nullable = false, unique = true)
    private String mail;

    /** 
     * Nombre del usuario.
     */
    private String name;

    /** 
     * Apellido del usuario. 
     * Mapeado como `last_name` en la base de datos.
     */
    @Column(name = "last_name")
    private String lastName;

    /** 
     * Contraseña del usuario. 
     */
    private String password;

    /** 
     * Fecha de registro del usuario en el sistema. 
     * Mapeado como `signup_date` en la base de datos.
     */
    @Column(name = "signup_date")
    private LocalDate signupDate;

    /**
     * Convierte el objeto User en un DTO de respuesta (UserResponse).
     * Este método se utiliza para enviar datos de usuario hacia el cliente.
     * 
     * @return Un objeto UserResponse con datos relevantes del usuario.
     */
    public UserResponse toDto() {
        UserResponse userResponse = new UserResponse();

        userResponse.setFullName(this.name + " " + this.lastName);
        userResponse.setMail(this.mail);
        userResponse.setSignupDate(this.signupDate.format(DateTimeFormatter.ofPattern("(dd/MM/yyyy)")));

        return userResponse;
    }

    /**
     * Convierte el objeto User en un DTO de actualización (UpdateUserResponse).
     * Este método se utiliza para actualizar datos de usuario.
     * 
     * @return Un objeto UpdateUserResponse con los datos actualizados del usuario.
     */
    public UpdateUserResponse toUpdateDto() {
        UpdateUserResponse respuesta = new UpdateUserResponse();

        respuesta.setFullName(this.name + " " + this.lastName);
        respuesta.setEmail(this.mail);
        respuesta.setPassword(this.password);

        return respuesta;
    }

    /**
     * Convierte el objeto User en un DTO de eliminación (DeleteUserResponse).
     * Este método se utiliza para enviar confirmación de eliminación de un usuario.
     * 
     * @return Un objeto DeleteUserResponse con los datos del usuario eliminado.
     */
    public DeleteUserResponse toDeleteDto() {
        DeleteUserResponse respuesta = new DeleteUserResponse();

        respuesta.setFullName(this.name + " " + this.lastName);
        respuesta.setMail(this.mail);

        return respuesta;
    }
}
