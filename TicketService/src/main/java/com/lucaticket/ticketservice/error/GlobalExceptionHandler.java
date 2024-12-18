package com.lucaticket.ticketservice.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lucaticket.ticketservice.error.exception.NoTicketsFoundException;
import com.lucaticket.ticketservice.error.exception.TicketAlreadyExistsException;
import com.lucaticket.ticketservice.error.exception.TicketNotFoundException;

/**
 * @author Alberto de la Blanca, Raul, Yuji, Angel
 * GlobalExceptionHandler se encarga de manejar excepciones en toda la aplicación.
 * 
 * Proporciona un manejo centralizado de excepciones para escenarios específicos como errores de validación,
 * excepciones personalizadas y errores generales, asegurando respuestas consistentes.
 * **/
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
     * Maneja TicketNotFoundException.
     *
     * @param ex la excepción lanzada cuando un ticket no es encontrado.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
     */
	
	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, "TICKET_NOT_FOUND", request);
	}
	
	/**
     * Maneja TicketAlreadyExistsException.
     *
     * @param ex la excepción lanzada cuando un ticket ya existe.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
     */
	@ExceptionHandler(TicketAlreadyExistsException.class)
	public ResponseEntity<Object> handleTicketAlreadyExistsException(TicketAlreadyExistsException ex,
			WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.CONFLICT, "TICKET_ALREADY_EXISTS", request);
	}

	/**
     * Maneja NoTicketsFoundException.
     *
     * @param ex la excepción lanzada cuando no se encuentran tickets.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
     */
	@ExceptionHandler(NoTicketsFoundException.class)
	public ResponseEntity<Object> handleNoTicketsFoundException(NoTicketsFoundException ex, WebRequest request) {
		return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, "NO_TICKETS_FOUND", request);
	}

	/**
     * Construye un ResponseEntity con una estructura de error estandarizada.
     *
     * @param message el mensaje de error.
     * @param status el código de estado HTTP.
     * @param errorCode un código de error personalizado.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
     */
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

	 /**
     * Maneja errores de validación para argumentos de métodos.
     *
     * @param ex la excepción lanzada cuando falla la validación.
     * @param headers los encabezados HTTP.
     * @param status el código de estado HTTP.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
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

	/**
     * Maneja errores relacionados con mensajes HTTP no legibles.
     *
     * @param ex la excepción lanzada cuando un mensaje no puede ser leído.
     * @param headers los encabezados HTTP.
     * @param status el código de estado HTTP.
     * @param request la solicitud web durante la cual ocurrió la excepción.
     * @return un ResponseEntity con los detalles del error.
     */
	@Override
	@Nullable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> messages = new ArrayList<>();
		CustomErrorJson customError = new CustomErrorJson();

		messages.add(ex.getLocalizedMessage());

		customError.setTimestamp(new Date());
		customError.setError(status.toString());
		customError.setMessage(messages);

		return new ResponseEntity<>(customError, headers, status);
	}
}
