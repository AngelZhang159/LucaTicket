package com.lucaticket.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lucaticket.user.error.InvalidDataException;
import com.lucaticket.user.model.User;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.repository.UserRepository;
import com.lucaticket.user.service.impl.UserServiceImpl;
import com.sun.jdi.request.EventRequest;

import jakarta.transaction.Transactional;

@SpringBootTest
class EventApplicationTests {
	// <-- Atributos -->

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void should_throw_invalid_data_exception_when_wrong_request() {
		
		when(userService.saveUser(any(UserRequest.class))).thenThrow(InvalidUserDataException.class);
		
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(new UserRequest()),
				"Debería lanzarse InvalidDataException cuando el DTO tiene datos inválidos.");
	}
	/**
	 * @author Alberto
	 * validates that an user with valid data persists in database
	 */
	 @Test
	 @Transactional
	  void registerUser_shouldPersistUserInDatabaseWhenDataIsValid() {
	        // Crear un objeto User con datos válidos
	        User validUser = new User();
	        validUser.setUserId("U12345");
	        validUser.setName("Juan");
	        validUser.setLastName("Pérez");
	        validUser.setMail("juan.perez@example.com");
	        validUser.setPassword("securePassword123");
	        validUser.setSignupDate(LocalDate.now());

	        // Registrar el usuario utilizando el servicio
	        User savedUser = userService.registerUser(validUser);

	        // Verificar que el usuario se haya persistido en la base de datos
	        assertNotNull(savedUser.getUserId(), "El ID del usuario debería generarse al persistirlo.");
	        assertNotNull(userRepository.findById(savedUser.getUserId()), "El usuario debería existir en la base de datos.");
	    }
}
