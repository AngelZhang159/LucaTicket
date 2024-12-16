package com.lucaticket.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que representa una respuesta resumida de un
 * evento.
 * Este objeto se utiliza para enviar información de borrado de un evento al
 * cliente.
 * 
 * @author Yuji
 * @version 1.0
 * @since 16/12/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateDelete {

    /** Mensaje de respuesta del servidor. */
    private String message = "El evento ha sido eliminado correctamente.";
    
    /** Identificador único del evento. */
    private long id;

    /** Nombre del evento. */
    private String name;
}
