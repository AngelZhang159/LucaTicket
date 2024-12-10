package com.lucaticket.event.error;

@ControllerAdvice
public class CustomHandlerException{

	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleInvalidDAtaException(InvalidDataException ex, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(
				HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage(),
	            request.getDescription(false)
				)
	}
	
}