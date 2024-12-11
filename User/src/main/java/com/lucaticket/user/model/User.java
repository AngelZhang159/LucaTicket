package com.lucaticket.user.model;

import java.time.LocalDate;

import com.lucaticket.user.model.dto.UserResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
        userResponse.setSignupDate(this.signupDate);

        return userResponse;
    }
}
