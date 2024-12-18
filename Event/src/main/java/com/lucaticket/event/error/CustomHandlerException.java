package com.lucaticket.event.error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import jakarta.servlet.http.HttpServletResponse;

/**
 * @since 11-12-2024
 * @version 4.0
 * Clase que maneja las excepciones de la aplicación de forma personalizada.
 * Esta clase extiende de {@link ResponseEntityExceptionHandler} para gestionar 
 * las excepciones de manera centralizada y personalizada en un controlador global.
 * 
 * Las excepciones manejadas incluyen datos inválidos, argumentos no válidos, errores de tipo 
 * y errores en los mensajes HTTP, entre otros.
 */
@RestControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

	 /**
     * Maneja excepciones de tipo {@link InvalidDataException}. Cuando se detecta una excepción de este tipo,
     * responde con un código de estado HTTP 400 (Bad Request).
     * 
     * @param response el objeto HttpServletResponse para enviar la respuesta
     * @throws IOException si ocurre un error al enviar la respuesta
     */
	@ExceptionHandler(InvalidDataException.class)
	public void springHandleNotFound(HttpServletResponse response) throws IOException {
		logger.info("------ DATOS INVALIDOS");
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(EventNotFoundException.class)
	public void handleEventNotFound(HttpServletResponse response) throws IOException {
		logger.info("------ DATOS INVALIDOS");
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	/**
     * Maneja excepciones de tipo {@link MethodArgumentNotValidException}, que ocurren cuando los argumentos 
     * del método no cumplen con las validaciones definidas en el controlador.
     * 
     * @param ex la excepción que contiene los errores de validación
     * @param headers los encabezados HTTP
     * @param status el estado HTTP de la respuesta
     * @param request la solicitud Web asociada a la excepción
     * @return una respuesta {@link ResponseEntity} con los detalles del error
     */

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

	/**
     * Maneja excepciones de tipo {@link TypeMismatchException}, que ocurren cuando hay un desajuste de tipo 
     * en los parámetros de la solicitud.
     * 
     * @param ex la excepción que contiene el error de tipo
     * @param headers los encabezados HTTP
     * @param status el estado HTTP de la respuesta
     * @param request la solicitud Web asociada a la excepción
     * @return una respuesta {@link ResponseEntity} con los detalles del error
     */
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

}