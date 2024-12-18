package com.lucaticket.compraservice.error;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lucaticket.compraservice.error.exception.BancoNoDisponibleException;
import com.lucaticket.compraservice.error.exception.CuentaNoRegistradaException;
import com.lucaticket.compraservice.error.exception.DatosCompraInvalidosException;
import com.lucaticket.compraservice.error.exception.EventoNotFoundException;
import com.lucaticket.compraservice.error.exception.InvalidDataException;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Raul, Alberto, Angel, Yuji
 */
@RestControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss");
	
	@ExceptionHandler(InvalidDataException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
		logger.info("------ DATOS INVALIDOS");
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(DatosCompraInvalidosException.class)
	public void datosCompraInvalidos(HttpServletResponse response) throws IOException {
		logger.info("------ DATOS INVALIDOS");
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler(CuentaNoRegistradaException.class) 
	public ResponseEntity<Object> handleCuentaNoRegistradaException(CuentaNoRegistradaException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, "CUENTA_NO_REGISTRADA", request);
	}
	
	@ExceptionHandler(EventoNotFoundException.class) 
	public ResponseEntity<Object> handleEventoNotFoundException(EventoNotFoundException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, "EVENTO_NO_EXISTE", request);
	}
	
	@ExceptionHandler(BancoNoDisponibleException.class) 
	public ResponseEntity<Object> handleBancoNoDisponibleException(BancoNoDisponibleException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, "SERVIDOR_NO_DISPONIBLE", request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		logger.info("------ handleMethodArgumentNotValid()");

		CustomErrorJson customError = new CustomErrorJson();

		customError.setTimestamp(new Date());
		customError.setStatus(status.value());
		customError.setError(status.toString());

		// Get all errors indicando el campo en el que falla
		List<String> messages = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			messages.add(error.getField() + ": " + error.getDefaultMessage());
		}
		customError.setMessage(messages);

		// Para recoger el path y simular de forma completa los datos originales
		// request.getDescription(false) ---> uri=/students
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=") + 1);
		customError.setPath(uri);

		return new ResponseEntity<>(customError, headers, status);

	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> messages = new ArrayList<>();
		CustomErrorJson customError = new CustomErrorJson();

		messages.add(ex.getLocalizedMessage());

		customError.setStatus(status.value());
		customError.setTimestamp(new Date());
		customError.setError(status.toString());
		customError.setMessage(messages);

		return new ResponseEntity<>(customError, headers, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		List<String> messages = new ArrayList<>();
		CustomErrorJson customError = new CustomErrorJson();

		messages.add("Revisa el formato de los campos. El id debe ser un número");

		customError.setStatus(status.value());
		customError.setTimestamp(new Date());
		customError.setError(status.toString());
		customError.setMessage(messages);

		return new ResponseEntity<>(customError, headers, status);
	}
	
	private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status, String errorCode,
			WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now().format(DATE_TIME_FORMATTER));
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		body.put("errorCode", errorCode);
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, status);
	}

}