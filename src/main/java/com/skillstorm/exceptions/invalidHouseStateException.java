package com.skillstorm.exceptions;


//Exception is thrown when we try to set an invalid state
public class invalidHouseStateException extends IllegalArgumentException {
	String message;

	public invalidHouseStateException() {
		super();
	}

	public invalidHouseStateException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "invalidHouseStateException [message=" + message + "]";
	}
	
	
}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////