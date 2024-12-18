package com.lucaticket.user.service;

import org.springframework.http.ResponseEntity;

import com.lucaticket.user.model.dto.UpdateUserResponse;
import com.lucaticket.user.model.dto.DeleteUserResponse;
import com.lucaticket.user.model.dto.UpdateUserRequest;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;

/**
 * Interfaz para la capa de servicio de gestión de usuarios.
 * Define los métodos principales para las operaciones CRUD relacionadas con
 * usuarios.
 * 
 * @author Angel
 * @version 1.0
 * @since 18-12-2024
 */
public interface UserService {

	/**
	 * Guarda un nuevo usuario en la base de datos.
	 * 
	 * @author Angel
	 * @param userRequest Datos del usuario a registrar.
	 * @return {@code ResponseEntity<UserResponse>} con los datos del usuario
	 *         creado.
	 * @since 18-12-2024
	 */
	public ResponseEntity<UserResponse> saveUser(UserRequest userRequest);

	/**
	 * Recupera un usuario por su email.
	 * 
	 * @author Raul
	 * @param email Email del usuario que se quiere buscar.
	 * @return {@code ResponseEntity<UserResponse>} con los datos del usuario
	 *         encontrado.
	 * @throws com.lucaticket.user.error.UserNotFoundException si no se encuentra el
	 *                                                         usuario.
	 * @since 18-12-2024
	 */
	public ResponseEntity<UserResponse> getUser(String email);

	/**
	 * Actualiza los datos de un usuario.
	 * 
	 * @author Raul
	 * @param email             Email del usuario a actualizar.
	 * @param updateUserRequest Datos de actualización.
	 * @return {@code ResponseEntity<UpdateUserResponse>} con los datos del usuario
	 *         actualizado.
	 * @throws com.lucaticket.user.error.InvalidUserDataException si la contraseña
	 *                                                            es incorrecta.
	 * @throws com.lucaticket.user.error.UserNotFoundException    si no se encuentra
	 *                                                            el usuario.
	 * @since 18-12-2024
	 */
	public ResponseEntity<UpdateUserResponse> update(String email, UpdateUserRequest updateUserRequest);

	/**
	 * Elimina un usuario de la base de datos.
	 * 
	 * @author Raul
	 * @param email Email del usuario a eliminar.
	 * @return {@code ResponseEntity<DeleteUserResponse>} con los datos del usuario
	 *         eliminado.
	 * @throws com.lucaticket.user.error.UserNotFoundException si no se encuentra el
	 *                                                         usuario.
	 * @since 18-12-2024
	 */
	public ResponseEntity<DeleteUserResponse> delete(String email);
}
