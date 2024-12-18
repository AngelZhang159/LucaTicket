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

// @Olivord
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String mail;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(name = "signup_date")
    private LocalDate signupDate;

    public UserResponse toDto() {
        UserResponse userResponse = new UserResponse();

        userResponse.setFullName(this.name + " " + this.lastName);
        userResponse.setMail(this.mail);
        userResponse.setSignupDate(this.signupDate.format(DateTimeFormatter.ofPattern("(dd/MM/yyyy)")));

        return userResponse;
    }
    
    public UpdateUserResponse toUpdateDto() {
    	UpdateUserResponse respuesta = new UpdateUserResponse();
    	
    	respuesta.setFullName(this.name + " " + this.lastName);
    	respuesta.setEmail(this.mail);
    	respuesta.setPassword(this.password);
    	
    	return respuesta;
    }
    
    public DeleteUserResponse toDeleteDto() {
    	DeleteUserResponse respuesta = new DeleteUserResponse();
    	
    	respuesta.setFullName(this.name + " " + this.lastName);
    	respuesta.setMail(this.mail);
    	return respuesta;
    }
}
