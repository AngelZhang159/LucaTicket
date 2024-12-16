package com.lucaticket.compraservice.model.dto;

import lombok.Data;

@Data
public class ValidarUserResponse {
	String name;
	String password;
	String token;
}
