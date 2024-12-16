package com.lucaticket.compraservice.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraRequest {

	@Email(message = "El email debe de tener un formato válido 'ejemplo@ejemplo.com'")
	@NotBlank(message = "El email no debe de estar vacío")
	private String email;
	@NotBlank(message = "El nombre de la tarjeta no puede estar vacío")
	private String nombreTitular;
	@Digits(integer = 16, fraction = 0, message = "El número de la tarjeta debe de tener 16 dígitos")
	@NotNull(message = "El número de la tarjeta no debe ser nulo")
	private Long numeroTarjeta;
	@Digits(integer = 2, fraction = 0, message = "El mes de la tarjeta debe de tener 2 dígitos")
	@Min(value = 1)
	@Max(value = 12)
	@NotNull(message = "El mes de la tarjeta no debe ser nulo")
	private Integer mesCaducidad;
	@Digits(integer = 4, fraction = 0, message = "El año de la tarjeta debe de tener 4 dígitos")
	@Min(value = 2024)
	@NotNull(message = "El año de la tarjeta no debe ser nulo")
	private Integer yearCaducidad;
	@Digits(integer = 3, fraction = 0, message = "El cvv debe de tener 3 dígitos")
	@NotNull(message = "El cvv de la tarjeta no debe ser nulo")
	private Integer cvv;
	private String emisor;
	private String concepto;
	private double cantidad;
	private Long idEvento;

}
