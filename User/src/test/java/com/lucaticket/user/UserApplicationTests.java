package com.lucaticket.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lucaticket.user.error.InvalidUserDataException;
import com.lucaticket.user.model.User;
import com.lucaticket.user.model.dto.UserRequest;
import com.lucaticket.user.model.dto.UserResponse;
import com.lucaticket.user.repository.UserRepository;
import com.lucaticket.user.service.impl.UserServiceImpl;

@SpringBootTest
class UserApplicationTests {
	// <-- Atributos -->

	@Autowired
	private UserServiceImpl userService;

	@MockBean
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

	@Test
	void registerUser_shouldReturn201WhenDataIsValid() {

		UserRequest userRequest = new UserRequest("Pepe", "Barrancos", "pepe@gmail.com", "ABCabc123&");
		User user = new User(1L, userRequest.getName(), userRequest.getLastName(), userRequest.getMail(),
				userRequest.getPassword(), LocalDate.now());

		when(userRepository.save(any(User.class))).thenReturn(user);

		ResponseEntity<UserResponse> responseEntity = userService.saveUser(userRequest);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
}
