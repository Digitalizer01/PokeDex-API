package com.pokedex.exception;

public class ResourceDuplicatedException extends RuntimeException {
	public ResourceDuplicatedException(String message) {
		super(message);
	}
}
