package com.lucaticket.compraservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidUser {
	private final String name = "Grupo02";
	private final String password = "AntoniosRules";
	private String token;
}
