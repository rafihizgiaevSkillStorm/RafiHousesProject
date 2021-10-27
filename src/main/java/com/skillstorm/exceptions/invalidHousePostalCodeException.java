package com.skillstorm.exceptions;

//Exception is thrown when we try to set an invalid postal code
public class invalidHousePostalCodeException extends IllegalArgumentException{
	
	String message;

	public invalidHousePostalCodeException() {
		super();
	}

	public invalidHousePostalCodeException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "invalidHousePostalCodeException [message=" + message + "]";
	}
	
	
}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////