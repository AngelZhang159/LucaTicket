package com.lucaticket.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucaticket.event.model.Event;

/**
 * @version 1.0
 * @since 12-12-2024
 * Repositorio para la entidad {@link Event}.
 * <p>
 * Esta interfaz extiende {@link JpaRepository} y permite realizar operaciones
 * CRUD sobre los eventos almacenados en la base de datos. Además, proporciona
 * un método adicional para buscar eventos por su nombre.
 * </p>
 */

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	 /**
     * Busca una lista de eventos por su nombre.
     * <p>
     * Este método permite realizar una búsqueda de eventos en la base de datos
     * que tengan el nombre especificado.
     * </p>
     * 
     * @param name el nombre del evento que se desea buscar
     * @return una lista de eventos que coinciden con el nombre proporcionado
     */
	
	List<Event> findByName(String name);
	
}
