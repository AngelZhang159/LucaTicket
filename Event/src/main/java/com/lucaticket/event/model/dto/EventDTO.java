package com.lucaticket.event.model.dto;

import java.time.LocalDateTime;

import com.lucaticket.event.model.enums.Genre;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa los datos completos de un evento.
 * Este objeto se utiliza para transferir información entre capas del sistema
 * o hacia el cliente.
 * 
 * @author Yuji
 * @version 2.0
 * @since 16-12-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    /** 
     * Identificador único del evento. 
     */
    @Positive(message = "El ID del evento debe ser un número positivo.")
    private long id;

    /** 
     * Nombre del evento. 
     * No puede estar vacío y debe tener un tamaño máximo de 50 caracteres.
     */
    @NotBlank(message = "El nombre del evento no puede estar vacío.")
    @Size(max = 50, message = "El nombre del evento no puede superar los 50 caracteres.")
    private String name;

    /** 
     * Descripción detallada del evento. 
     * No puede estar vacía y debe tener un tamaño máximo de 500 caracteres.
     */
    @NotBlank(message = "La descripción del evento no puede estar vacía.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String description;

    /** 
     * Fecha y hora en la que se llevará a cabo el evento. 
     * Debe ser una fecha presente o futura.
     */
    @NotNull(message = "La fecha del evento no puede estar vacía.")
    @FutureOrPresent(message = "La fecha del evento debe ser presente o futura.")
    private LocalDateTime eventDate;

    /** 
     * Precio mínimo de entrada para el evento. 
     * Debe ser un valor positivo.
     */
    @NotNull(message = "El precio mínimo no puede estar vacío.")
    @Positive(message = "El precio mínimo debe ser un número positivo.")
    private double minPrice;

    /** 
     * Precio máximo de entrada para el evento. 
     * Debe ser un valor positivo.
     */
    @NotNull(message = "El precio máximo no puede estar vacío.")
    @Positive(message = "El precio máximo debe ser un número positivo.")
    private double maxPrice;

    /** 
     * Localización del evento, como ciudad o dirección específica. 
     * No puede estar vacía y debe tener un tamaño máximo de 100 caracteres.
     */
    @NotBlank(message = "La localización del evento no puede estar vacía.")
    @Size(max = 100, message = "La localización no puede superar los 100 caracteres.")
    private String location;

    /** 
     * Nombre del recinto donde se llevará a cabo el evento. 
     * No puede estar vacío y debe tener un tamaño máximo de 50 caracteres.
     */
    @NotBlank(message = "El nombre del recinto no puede estar vacío.")
    @Size(max = 50, message = "El nombre del recinto no puede superar los 50 caracteres.")
    private String venueName;

    /** 
     * Género musical o tipo de evento, representado como un enumerado. 
     * No puede ser nulo.
     */
    @NotNull(message = "El género del evento no puede estar vacío.")
    private Genre genre;
}
