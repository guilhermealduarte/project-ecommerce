package com.guilhermeduarte.projectecommerce.services.exceptions;

public class ResourceNotFoundException  extends RuntimeException{
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
