package com.pokedex.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceDuplicatedException.class)
	public ResponseEntity<Map<String, String>> handleResourceDuplicatedException(ResourceDuplicatedException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", new Date().toString());
		errorResponse.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
		errorResponse.put("error", ex.getMessage());
		System.out.println(errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // 409 Conflict
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", new Date().toString());
		errorResponse.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
		errorResponse.put("error", ex.getMessage());
		System.out.println(errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // 404 Not Found
	}

	@ExceptionHandler(ResourceInvalidDataException.class)
	public ResponseEntity<Map<String, String>> handleResourceInvalidDataException(ResourceInvalidDataException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", new Date().toString());
		errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
		errorResponse.put("error", ex.getMessage());
		System.out.println(errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad request
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", new Date().toString());
		errorResponse.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		errorResponse.put("error", ex.getMessage());
		System.out.println(errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	}
}
